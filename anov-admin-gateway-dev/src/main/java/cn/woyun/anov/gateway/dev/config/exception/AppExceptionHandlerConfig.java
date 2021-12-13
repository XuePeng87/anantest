package cn.woyun.anov.gateway.dev.config.exception;

import cn.woyun.anov.gateway.dev.enums.AppResultStatus;
import cn.woyun.anov.gateway.dev.exception.app.*;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCodeExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * APP异常统一处理类。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class AppExceptionHandlerConfig {

    /**
     * 控制器登录密码解析失败。
     */
    @ExceptionHandler(value = DevAppLoginPasswordDecodeException.class)
    @ResponseBody
    public HttpResult<String> devAppLoginPasswordDecodeExceptionHandler(DevAppLoginPasswordDecodeException e) {
        return new HttpResult.Builder<String>(AppResultStatus.PASSWORD_DECODE)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器登录时验证码过期或不存在。
     */
    @ExceptionHandler(value = VerifyCodeExpiredException.class)
    @ResponseBody
    public HttpResult<String> verifyCodeExpiredExceptionHandler(VerifyCodeExpiredException e) {
        return new HttpResult.Builder<String>(AppResultStatus.VERIFY_CODE_EXPIRED)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器登录时验证码不正确。
     */
    @ExceptionHandler(value = DevAppLoginVerifyCodeIncorrectException.class)
    @ResponseBody
    public HttpResult<String> devAppLoginVerifyCodeIncorrectExceptionHandler(DevAppLoginVerifyCodeIncorrectException e) {
        return new HttpResult.Builder<String>(AppResultStatus.VERIFY_CODE_INCORREC)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器登录时用户名或密码错误。
     */
    @ExceptionHandler(value = DevAppLoginFailedException.class)
    @ResponseBody
    public HttpResult<String> devAppLoginFailedExceptionHandler(DevAppLoginFailedException e) {
        return new HttpResult.Builder<String>(AppResultStatus.LOGIN_FAILED)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器控制的目标不存在
     */
    @ExceptionHandler(value = DevAppTargetNotFoundException.class)
    @ResponseBody
    public HttpResult<String> devAppTargetNotFoundExceptionHandler(DevAppTargetNotFoundException e) {
        return new HttpResult.Builder<String>(AppResultStatus.TARGET_NOT_FOUND)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器控制的目标未上线
     */
    @ExceptionHandler(value = DevAppTargetOfflineException.class)
    @ResponseBody
    public HttpResult<String> devAppTargetOfflineExceptionHandler(DevAppTargetOfflineException e) {
        return new HttpResult.Builder<String>(AppResultStatus.TARGET_OFFLINE)
                .msg(e.getMessage()).build();
    }

}
