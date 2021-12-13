package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.gateway.management.enums.SystemResultStatus;
import cn.woyun.anov.gateway.management.exception.LoginFailedException;
import cn.woyun.anov.gateway.management.exception.LoginPasswordDecodeException;
import cn.woyun.anov.gateway.management.exception.VerifyCodeIncorrectException;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCodeExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统管理功能的异常处理。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class SystemExceptionHandlerConfig {


    /**
     * 登录密码解密失败。
     */
    @ExceptionHandler(value = LoginPasswordDecodeException.class)
    @ResponseBody
    public HttpResult<String> loginPasswordDecodeExceptionHandler(LoginPasswordDecodeException e) {
        log.error(e.getMessage());
        return new HttpResult.Builder<String>(SystemResultStatus.PASSWORD_DECODE_FAILED)
                .msg(e.getMessage()).build();
    }

    /**
     * 登录时验证码过期或不存在。
     */
    @ExceptionHandler(value = VerifyCodeExpiredException.class)
    @ResponseBody
    public HttpResult<String> verifyCodeExpiredExceptionHandler(VerifyCodeExpiredException e) {
        return new HttpResult.Builder<String>(SystemResultStatus.VERIFY_CODE_EXPIRED)
                .msg(e.getMessage()).build();
    }

    /**
     * 登录时验证码输入不正确。
     */
    @ExceptionHandler(value = VerifyCodeIncorrectException.class)
    @ResponseBody
    public HttpResult<String> verifyCodeIncorrectExceptionHandler(VerifyCodeIncorrectException e) {
        return new HttpResult.Builder<String>(SystemResultStatus.VERIFY_CODE_INCORREC)
                .msg(e.getMessage()).build();
    }

    /**
     * 登录时用户名或密码不正确。
     */
    @ExceptionHandler(value = LoginFailedException.class)
    @ResponseBody
    public HttpResult<String> loginFailedExceptionHandler(LoginFailedException e) {
        return new HttpResult.Builder<String>(SystemResultStatus.LOGIN_FAILED)
                .msg(e.getMessage()).build();
    }

}
