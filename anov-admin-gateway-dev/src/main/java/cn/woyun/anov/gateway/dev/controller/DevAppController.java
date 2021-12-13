package cn.woyun.anov.gateway.dev.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.codec.RsaUtils;
import cn.woyun.anov.config.mgt.SysUserConfiguration;
import cn.woyun.anov.gateway.dev.bean.request.AppLoginRequestBean;
import cn.woyun.anov.gateway.dev.bean.resposne.DevAppLoginResponseBean;
import cn.woyun.anov.gateway.dev.bean.resposne.DevScreenClientResponseBean;
import cn.woyun.anov.gateway.dev.exception.app.DevAppLoginPasswordDecodeException;
import cn.woyun.anov.gateway.dev.exception.app.DevAppLoginVerifyCodeIncorrectException;
import cn.woyun.anov.gateway.dev.serivce.DevAppServiceProxy;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCode;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCodeService;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 控制器的API类。
 *
 * @author xuepeng
 */
@RestController
@Slf4j
@RequestMapping("/apps")
@Api(tags = "控制器API接口")
@SaCheckLogin
public class DevAppController extends BaseController {

    /**
     * 获取验证码。
     *
     * @return 验证码。
     */
    @GetMapping("/v1/verify-code")
    @ApiOperation(value = "获取验证码")
    public HttpResult<VerifyCode> createVerifyCode() {
        return DefaultHttpResultFactory.success("获取验证码成功。", verifyCodeService.create());
    }

    /**
     * 登录。
     *
     * @param appLoginRequestBean 请求登录参数对象。
     * @return 是否登录成功。
     */
    @PostMapping("/v1/login")
    @ApiOperation(value = "登录")
    public HttpResult<DevAppLoginResponseBean> login(@RequestBody final AppLoginRequestBean appLoginRequestBean) {
        // 根据RSA解密密码
        final String password;
        try {
            password = RsaUtils.decryptByPrivateKey(sysUserConfiguration.getPasswordPrivate(), appLoginRequestBean.getPassword());
        } catch (Exception e) {
            log.info("app login password decode error, password is {}, cause is {}", appLoginRequestBean.getPassword(), e.getMessage());
            throw new DevAppLoginPasswordDecodeException("密码解密失败。");
        }
        // 判断验证码是否正确
        if (verifyCodeService.validate(appLoginRequestBean.getUuid(), appLoginRequestBean.getCode())) {
            throw new DevAppLoginVerifyCodeIncorrectException("验证码不正确。");
        }
        return DefaultHttpResultFactory.success("登录成功", devAppServiceProxy.login(appLoginRequestBean.getUsername(), password));
    }

    /**
     * 根据客户端唯一标识查询大屏唯一标识。
     *
     * @param code 客户端唯一标识。
     * @return 大屏唯一标识。
     */
    @GetMapping("/v1/clients/{code}")
    @ApiOperation(value = "根据ClientCode查询大屏Key")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "客户端唯一标识", required = true)
    })
    public HttpResult<String> findDevScreenKeyByCode(@Valid @NotBlank(message = "客户端标识不能为空")
                                                     @Length(max = 6, message = "客户端标识长度不能大于6")
                                                     @PathVariable(value = "code") final String code) {
        final String devScreenKey = devAppServiceProxy.findDevScreenKeyByClientCode(code);
        return DefaultHttpResultFactory.success("查询大屏唯一标识。", devScreenKey);
    }

    /**
     * 根据客户端唯一标识获取前三个在线大历史大屏信息。
     *
     * @param code 客户端唯一标识。
     * @return 客户端集合。
     */
    @GetMapping("/v1/clients/{code}/history")
    @ApiOperation(value = "根据ClientCode获取Top3在线历史大屏")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "客户端唯一标识", required = true)
    })
    public HttpResult<List<DevScreenClientResponseBean>> findOnlineTop3HistoryScreenByCode(@Valid @NotBlank(message = "客户端标识不能为空")
                                                                                           @Length(max = 6, message = "客户端标识长度不能大于6")
                                                                                           @PathVariable(value = "code") final String code) {
        final List<DevScreenClient> clients = devAppServiceProxy.findOnlineTop3HistoryScreenByCode(code);
        return DefaultHttpResultFactory.success("查询连接过的大屏。", BeanUtil.listObjToListObj(clients, DevScreenClientResponseBean.class));
    }

    /**
     * 验证控制器。
     *
     * @param key 大屏唯一标识。
     */
    @GetMapping("/v1/{key}/verify")
    @ApiOperation(value = "控制器认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Token", value = "访问令牌", required = true),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Client-Code", value = "客户端唯一标识"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Clinet-Target-Code", value = "目标客户端唯一标识"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Client-Type", value = "客户端类型"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Version", value = "大屏框架版本"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Core-Version", value = "大屏内核版本"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Level", value = "大屏等级"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Debug", value = "大屏开发环境"),
            @ApiImplicitParam(paramType = "header", name = "X-Anov-Dir", value = "大屏开发目录"),
            @ApiImplicitParam(paramType = "header", name = "GA", value = "统计分析数据"),
            @ApiImplicitParam(paramType = "path", name = "key", value = "大屏唯一标识", required = true)
    })
    public HttpResult<Boolean> verify(@Valid @NotBlank(message = "大屏标识不能为空")
                                      @Length(max = 12, message = "大屏标识长度不能大于12")
                                      @PathVariable(value = "key") final String key) {
        if (devAppServiceProxy.verify(key, super.getHttpServletRequest())) {
            return DefaultHttpResultFactory.success("验证成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("验证失败。", Boolean.FALSE);
    }

    /**
     * 自动装配验证码的业务处理接口。
     *
     * @param verifyCodeService 验证码的业务处理接口。
     */
    @Autowired
    public void setVerifyCodeService(VerifyCodeService verifyCodeService) {
        this.verifyCodeService = verifyCodeService;
    }

    /**
     * 自动装配大屏控制器的业务处理接口。
     *
     * @param devAppServiceProxy 大屏控制器的业务处理接口。
     */
    @Autowired
    public void setDevAppServiceProxy(DevAppServiceProxy devAppServiceProxy) {
        this.devAppServiceProxy = devAppServiceProxy;
    }

    /**
     * 自动装配用户管理配置信息。
     *
     * @param sysUserConfiguration 用户管理配置信息。
     */
    @Autowired
    public void setSysUserConfiguration(SysUserConfiguration sysUserConfiguration) {
        this.sysUserConfiguration = sysUserConfiguration;
    }

    /**
     * 验证码的业务处理接口。
     */
    private VerifyCodeService verifyCodeService;

    /**
     * 大屏控制器的业务处理接口。
     */
    private DevAppServiceProxy devAppServiceProxy;

    /**
     * 用户管理配置信息。
     */
    private SysUserConfiguration sysUserConfiguration;

}
