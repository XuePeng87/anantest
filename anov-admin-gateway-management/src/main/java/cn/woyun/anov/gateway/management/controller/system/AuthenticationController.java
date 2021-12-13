package cn.woyun.anov.gateway.management.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.woyun.anov.codec.RsaUtils;
import cn.woyun.anov.config.mgt.SysUserConfiguration;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.bean.request.system.UserLoginRequestBean;
import cn.woyun.anov.gateway.management.bean.response.system.LoginSuccessResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.gateway.management.exception.LoginFailedException;
import cn.woyun.anov.gateway.management.exception.LoginPasswordDecodeException;
import cn.woyun.anov.gateway.management.exception.VerifyCodeIncorrectException;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCode;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCodeService;
import cn.woyun.anov.sdk.mgt.entity.SysUser;
import cn.woyun.anov.sdk.mgt.exception.SysUserNotFoundException;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 验证码的API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/auth")
@Api(tags = "身份认证API接口")
@Slf4j
@SaCheckLogin
public class AuthenticationController extends BaseController {

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
     * 登录系统。
     *
     * @param userLoginRequestBean 登录的请求参数对象。
     * @return 是否登录成功。
     */
    @PostMapping("/v1/login")
    @OperationLog(system = "管理平台", module = "登录", description = "用户登录", type = OperationTypeEnum.LOGIN)
    @ApiOperation(value = "系统登录")
    public HttpResult<LoginSuccessResponseBean> login(@Valid @RequestBody UserLoginRequestBean userLoginRequestBean) {
        // 根据RSA解密密码
        final String password;
        try {
            password = RsaUtils.decryptByPrivateKey(sysUserConfiguration.getPasswordPrivate(), userLoginRequestBean.getPassword());
        } catch (Exception e) {
            log.info("login password decode error, password is {}, cause is {}", userLoginRequestBean.getPassword(), e.getMessage());
            throw new LoginPasswordDecodeException("密码解密失败。");
        }
        // 判断验证码是否正确
        if (!verifyCodeService.validate(userLoginRequestBean.getUuid(), userLoginRequestBean.getCode())) {
            throw new VerifyCodeIncorrectException("验证码不正确。");
        }
        try {
            SysUser sysUser = sysUserService.findByAccountAndPassword(userLoginRequestBean.getUsername(), password);
            // 登录成功
            StpUtil.login(sysUser.getId());
            StpUtil.getSession().set("user", sysUser);
            // 查询用户信息
            sysUser = sysUserService.findById(sysUser.getId());
            StpUtil.getSession().set("user", sysUser);
            // 返回数据
            final LoginSuccessResponseBean result = new LoginSuccessResponseBean();
            result.setTokenName(StpUtil.getTokenName());
            result.setTokenValue(StpUtil.getTokenValue());
            result.setSysUser(sysUser);
            return DefaultHttpResultFactory.success("登录成功", result);
        } catch (SysUserNotFoundException e) {
            throw new LoginFailedException("用户名或密码不正确。");
        }
    }

    /**
     * 登出系统。
     *
     * @return 是否登出成功。
     */
    @PostMapping("/v1/logout")
    @OperationLog(system = "管理平台", module = "登出", description = "用户登出", type = OperationTypeEnum.LOGOUT)
    @ApiOperation(value = "系统登出")
    public HttpResult<Boolean> logout() {
        StpUtil.getSession().delete("user");
        StpUtil.logout(StpUtil.getLoginIdAsLong());
        return DefaultHttpResultFactory.success("登出成功。", Boolean.TRUE);
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
