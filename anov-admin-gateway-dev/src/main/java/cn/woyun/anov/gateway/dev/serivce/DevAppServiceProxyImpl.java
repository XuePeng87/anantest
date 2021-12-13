package cn.woyun.anov.gateway.dev.serivce;

import cn.dev33.satoken.stp.StpUtil;
import cn.woyun.anov.gateway.dev.bean.resposne.DevAppLoginResponseBean;
import cn.woyun.anov.gateway.dev.consts.DevScreenConsts;
import cn.woyun.anov.gateway.dev.exception.app.DevAppLoginFailedException;
import cn.woyun.anov.gateway.dev.exception.app.DevAppTargetNotFoundException;
import cn.woyun.anov.gateway.dev.exception.app.DevAppTargetOfflineException;
import cn.woyun.anov.random.RandomUtil;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import cn.woyun.anov.sdk.mgt.entity.SysUser;
import cn.woyun.anov.sdk.mgt.exception.SysUserNotFoundException;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 控制客户端的业务处理接口。
 *
 * @author xuepeng
 */
@Service
@Slf4j
public class DevAppServiceProxyImpl implements DevAppServiceProxy {

    /**
     * 控制客户端登录。
     *
     * @param username 用户名。
     * @param password 密码。
     * @return 是否登录成功。
     */
    @Override
    public DevAppLoginResponseBean login(final String username, final String password) {
        // 进行登录
        try {
            final SysUser sysUser = sysUserService.findByAccountAndPassword(username, password);
            StpUtil.login(sysUser.getId());
            final DevAppLoginResponseBean result = new DevAppLoginResponseBean();
            result.setTokenName(StpUtil.getTokenName());
            result.setTokenValue(StpUtil.getTokenValue());
            result.setClientCode(generateClientCode());
            return result;
        } catch (SysUserNotFoundException e) {
            throw new DevAppLoginFailedException(e.getMessage());
        }
    }

    /**
     * 根据客户端唯一标识查询大屏唯一标识。
     *
     * @param code 客户端唯一标识。
     * @return 大屏唯一标识。
     */
    @Override
    public String findDevScreenKeyByClientCode(final String code) {
        final DevScreenClient devScreenClient = devScreenClientService.findScreenByCode(code);
        return Objects.isNull(devScreenClient) ? StringUtils.EMPTY : devScreenClient.getScreenKey();
    }

    /**
     * 根据客户端唯一标识获取前三个在线大历史大屏信息。
     *
     * @param code 客户端唯一标识。
     * @return 客户端集合。
     */
    @Override
    public List<DevScreenClient> findOnlineTop3HistoryScreenByCode(final String code) {
        return devScreenClientService.findOnlineTop3HistoryScreenByCode(code);
    }

    /**
     * 认证控制器。
     *
     * @param key     大屏唯一标识。
     * @param request 客户端请求。
     * @return 是否认证通过。
     */
    @Override
    public boolean verify(final String key, final HttpServletRequest request) {
        final String clientCode = request.getHeader(DevScreenConsts.CLIENT_CODE);
        final String screenCode = request.getHeader(DevScreenConsts.CLIENT_TARGET_CODE);
        // 判断要连接的大屏是否存在
        final DevScreenClient screenClient =
                devScreenClientService.findScreenByKeyAndCode(key, screenCode);
        if (Objects.isNull(screenClient)) {
            log.info("screen client not found, code is {}", screenCode);
            throw new DevAppTargetNotFoundException("要控制的大屏不存在。");
        }
        if (BooleanUtils.isFalse(screenClient.getClientOnlined())) {
            log.info("screen client is offline. code is {}", screenCode);
            throw new DevAppTargetOfflineException("要控制的大屏未上线。");
        }
        final DevScreenClient devScreenClient = devScreenClientCreator.create(request);
        devScreenClient.setScreenKey(key);
        devScreenClient.setClientCode(clientCode);
        devScreenClient.setClientRemoteCode(screenCode);
        return devScreenClientService.createOrUpdate(devScreenClient);
    }

    /**
     * 生成客户端唯一标识。
     *
     * @return 客户端唯一标识。
     */
    private String generateClientCode() {
        String code;
        do {
            code = RandomUtil.getSixDigitsString();
        } while (devScreenClientService.checkClientCodeExist(code));
        return code;
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
     * 自动装配用户业务处理接口。
     *
     * @param sysUserService 用户业务处理接口。
     */
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
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
     * 大屏客户端的业务处理接口。
     */
    private DevScreenClientService devScreenClientService;

    /**
     * 用户业务处理接口。
     */
    private SysUserService sysUserService;

    /**
     * 大屏客户端创建器。
     */
    private DevScreenClientCreator devScreenClientCreator;

}
