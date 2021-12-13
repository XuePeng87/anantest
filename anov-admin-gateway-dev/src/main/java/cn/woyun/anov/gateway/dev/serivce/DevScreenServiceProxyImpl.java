package cn.woyun.anov.gateway.dev.serivce;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.codec.AesUtil;
import cn.woyun.anov.config.dev.DevScreenConfiguration;
import cn.woyun.anov.consts.PunctuationConst;
import cn.woyun.anov.gateway.dev.bean.resposne.DevScreenResponseBean;
import cn.woyun.anov.gateway.dev.consts.DevScreenConsts;
import cn.woyun.anov.gateway.dev.exception.screen.*;
import cn.woyun.anov.random.RandomUtil;
import cn.woyun.anov.sdk.dev.entity.DevScreen;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.entity.DevScreenPic;
import cn.woyun.anov.sdk.dev.enums.ScreenStatus;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import cn.woyun.anov.sdk.dev.service.DevScreenPicService;
import cn.woyun.anov.sdk.dev.service.DevScreenService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * 大屏的业务处理实现类。
 *
 * @author xuepeng
 */
@Service
@Slf4j
public class DevScreenServiceProxyImpl implements DevScreenServiceProxy {

    /**
     * 认证大屏。
     *
     * @param key     大屏唯一标识。
     * @param request 客户端请求。
     * @return 是否验证通过。
     */
    @Override
    public DevScreenResponseBean verify(final String key, final HttpServletRequest request) {
        // 解密token
        final String token = decodeToken(request.getHeader(DevScreenConsts.CLIENT_TOKEN));
        // 解析token内容
        final DevScreenToken devScreenToken = formatToken(token);
        // 签名中的标识与请求的标识不一致
        checkKey(key, devScreenToken.getKey());
        // 请求时间区间过大，认证不通过
        // TODO 每次请求都需要验证
        // checkRequestTime(devScreenToken.getTime());
        // 判断大屏是否存在
        final DevScreen devScreen = checkScreenExisted(key);
        // 验证大屏过期时间
        checkScreenExpired(devScreen.getScreenExpireTime());
        // 判断大屏是否不可用
        checkScreenStatus(devScreen);

        // 保存大屏信息
        final String clientCode = request.getHeader(DevScreenConsts.CLIENT_CODE);
        final DevScreenClient devScreenClient = devScreenClientCreator.create(request);
        devScreenClient.setScreenKey(key);
        devScreenClient.setTenantId(devScreen.getTenantId());
        devScreenClient.setClientCode(this.generateClientCode(clientCode));
        devScreenClientService.createOrUpdate(devScreenClient);

        // 转换验证返回数据
        final DevScreenResponseBean result = BeanUtil.objToObj(devScreen, DevScreenResponseBean.class);
        final byte[] screenCore = devScreen.getScreenCore();
        result.setScreenCore(new String(screenCore));
        result.setClientCode(devScreenClient.getClientCode());
        result.setClientIp(devScreenClient.getClientRequestIp());
        result.setClientIpInfo(devScreenClient.getClientRequestIpInfo());
        result.setVerify(Boolean.TRUE);
        return result;
    }

    /**
     * 保存大屏监控图片。
     *
     * @param key  大屏唯一标识。
     * @param url  图片Url。
     * @param page 页面。
     * @return 是否保存成功。
     */
    @Override
    public boolean saveMonitorPic(final String key, final String url, final String page) {
        final DevScreenPic devScreenPic = new DevScreenPic();
        devScreenPic.setScreenKey(key);
        devScreenPic.setMonitorImg(url);
        devScreenPic.setMonitorPage(page);
        return devScreenPicService.saveDevScreenPic(devScreenPic);
    }

    /**
     * 生成客户端唯一标识。
     * 如果标识等于空或者undefined，生成一个新的标识返回给客户端。
     *
     * @param clientCode 客户端唯一标识。
     * @return 客户端唯一标识。
     */
    private String generateClientCode(final String clientCode) {
        if (StringUtils.isBlank(clientCode) || StringUtils.equals(clientCode, "undefined")) {
            String code;
            do {
                code = RandomUtil.getSixDigitsString();
            } while (devScreenClientService.checkClientCodeExist(code));
            return code;
        }
        return clientCode;
    }

    /**
     * 解密token
     */
    private String decodeToken(final String token) {
        try {
            return AesUtil.decrypt(token);
        } catch (Exception e) {
            log.info("aes decode error, token = {}", token);
            throw new DevScreenTokenDecodeException("大屏令牌无法解密。");
        }
    }

    /**
     * 格式化token
     */
    private DevScreenToken formatToken(final String token) {
        final DevScreenToken devScreenToken = new DevScreenToken();
        // 解析token内容
        if (StringUtils.indexOf(token, PunctuationConst.VERTICAL_LINE) < 0) {
            log.info("chat '|' not in token, can not format, keyAndRequestTimeStr = {}", token);
            throw new DevScreenTokenFormatException("大屏令牌无法解析。");
        }
        final String[] params = StringUtils.split(token, PunctuationConst.VERTICAL_LINE);
        devScreenToken.setKey(params[0]);
        devScreenToken.setTime(Long.parseLong(params[1]));
        return devScreenToken;
    }

    /**
     * 验证token中的key是否正确
     */
    private void checkKey(final String paramKey, final String tokenKey) {
        if (!StringUtils.equals(paramKey, tokenKey)) {
            log.info("key in token not equals key in params, paramKey = {}, tokenKey = {}", paramKey, tokenKey);
            throw new DevScreenTokenIncorrectException("大屏令牌与项目唯一标识不匹配。");
        }
    }

    /**
     * 验证大屏请求时间区间
     */
    private void checkRequestTime(final long tokenTime) {
        final long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (now - tokenTime > devScreenConfiguration.getRequestTimeRange().toMillis()) {
            log.info("request time not in max interval, now = {}, tokenTime = {}, maxInterval = {}, now - TokenTime = {}",
                    now, tokenTime, devScreenConfiguration.getRequestTimeRange().toMillis(), now - tokenTime);
            throw new DevScreenRequestTimeRangeException("请求头时间区间过长。");
        }
    }

    /**
     * 验证大屏是否存在
     */
    private DevScreen checkScreenExisted(final String key) {
        final DevScreen devScreen = devScreenService.findByKey(key);
        if (Objects.isNull(devScreen)) {
            log.info("dev not in database, key = {}", key);
            throw new DevScreenNotFoundException("大屏不存在。");
        }
        return devScreen;
    }

    /**
     * 验证大屏有效期是否过期
     */
    private void checkScreenExpired(final LocalDateTime expireTime) {
        final long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        final long time = expireTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (now > time) {
            log.info("dev expire, now = {}, expire = {}", now, time);
            throw new DevScreenExpireException("大屏授权已过期。");
        }
    }

    /**
     * 验证大屏状态是否不可用。
     */
    private void checkScreenStatus(final DevScreen devScreen) {
        if (devScreen.getScreenStatus() == ScreenStatus.DISABLE.ordinal()) {
            log.info("dev status is idsable. key is {}", devScreen.getScreenKey());
            throw new DevScreenStatusException("大屏状态为不可用。");
        }
    }

    /**
     * 大屏Token对象。
     *
     * @author xuepeng
     */
    @Data
    private static class DevScreenToken {

        /**
         * 大屏标识。
         */
        private String key;
        /**
         * 请求时间。
         */
        private long time;

    }

    /**
     * 自动装配大屏的业务处理接口。
     *
     * @param devScreenService 大屏的业务处理接口。
     */
    @Autowired
    public void setDevScreenService(DevScreenService devScreenService) {
        this.devScreenService = devScreenService;
    }

    /**
     * 自动装配大屏客户端的业务处理接口。
     *
     * @param devScreenClientService 大屏客户端的业务处理接口。
     */
    @Autowired
    public void setDevScreenClientService(DevScreenClientService devScreenClientService) {
        this.devScreenClientService = devScreenClientService;
    }

    /**
     * 自动装配大屏监控图片业务处理接口。
     *
     * @param devScreenPicService 大屏监控图片业务处理接口。
     */
    @Autowired
    public void setDevScreenPicService(DevScreenPicService devScreenPicService) {
        this.devScreenPicService = devScreenPicService;
    }

    /**
     * 自动装配大屏管理的配置信息。
     *
     * @param devScreenConfiguration 大屏管理的配置信息。
     */
    @Autowired
    public void setDevScreenConfiguration(DevScreenConfiguration devScreenConfiguration) {
        this.devScreenConfiguration = devScreenConfiguration;
    }

    /**
     * 自动装配大屏客户端创建器。
     *
     * @param devScreenClientCreator 大屏客户端创建器。
     */
    @Autowired
    public void setDevScreenClientCreator(DevScreenClientCreator devScreenClientCreator) {
        this.devScreenClientCreator = devScreenClientCreator;
    }

    /**
     * 大屏的业务处理接口。
     */
    private DevScreenService devScreenService;

    /**
     * 大屏客户端的业务处理接口。
     */
    private DevScreenClientService devScreenClientService;

    /**
     * 大屏监控图片业务处理接口。
     */
    private DevScreenPicService devScreenPicService;

    /**
     * 大屏管理的配置信息。
     */
    private DevScreenConfiguration devScreenConfiguration;

    /**
     * 大屏客户端创建器。
     */
    private DevScreenClientCreator devScreenClientCreator;

}
