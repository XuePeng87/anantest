package cn.woyun.anov.gateway.management.controller.system;

import cn.woyun.anov.config.mgt.SysUserConfiguration;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCode;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCodeService;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码的API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/auth")
@Api(tags = "验证码API接口")
@Slf4j
public class AuthenticationController {

    /**
     * 获取验证码。
     *
     * @return 验证码。
     */
    @GetMapping("/v1/verify-code")
    @ApiOperation(value = "获取验证码")
    public HttpResult<VerifyCode> createVerifyCode() {
        return DefaultHttpResultFactory.success("获取验证码成功。",
                verifyCodeService.create());
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
     * 自动装配用户管理配置信息。
     *
     * @param sysUserConfiguration 用户管理配置信息。
     */
    @Autowired
    public void setSysUserConfiguration(SysUserConfiguration sysUserConfiguration) {
        this.sysUserConfiguration = sysUserConfiguration;
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
     * 验证码的业务处理接口。
     */
    private VerifyCodeService verifyCodeService;

    /**
     * 用户管理配置信息。
     */
    private SysUserConfiguration sysUserConfiguration;

    /**
     * 用户业务处理接口。
     */
    private SysUserService sysUserService;

}
