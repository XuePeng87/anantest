package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.gateway.management.enums.UserResultStatus;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户的异常处理。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class UserExceptionHandlerConfig {

    /**
     * 用户帐号已存在。
     */
    @ExceptionHandler(value = SysUserAccountDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysUserAccountDuplicateExceptionHandler(SysUserAccountDuplicateException e) {
        return new HttpResult.Builder<String>(UserResultStatus.ACCOUNT_DUPLICATE)
                .msg(e.getMessage()).build();
    }

    /**
     * 用户电话号已存在。
     */
    @ExceptionHandler(value = SysUserPhoneDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysUserPhoneDuplicateExceptionHandler(SysUserPhoneDuplicateException e) {
        return new HttpResult.Builder<String>(UserResultStatus.PHONE_DUPLICATE)
                .msg(e.getMessage()).build();
    }

    /**
     * 用户邮箱已存在。
     */
    @ExceptionHandler(value = SysUserEmailDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysUserEmailDuplicateExceptionHandler(SysUserEmailDuplicateException e) {
        return new HttpResult.Builder<String>(UserResultStatus.EMAIL_DUPLICATE)
                .msg(e.getMessage()).build();
    }

    /**
     * 旧密码不正确。
     */
    @ExceptionHandler(value = SysUserOldPasswordIncorrectException.class)
    @ResponseBody
    public HttpResult<String> sysUserOldPasswordIncorrectExceptionHandler(SysUserOldPasswordIncorrectException e) {
        return new HttpResult.Builder<String>(UserResultStatus.OLD_PASSWORD_INCORRECT)
                .msg(e.getMessage()).build();
    }

    /**
     * 用户不存在。
     */
    @ExceptionHandler(value = SysUserNotFoundException.class)
    @ResponseBody
    public HttpResult<String> sysUserNotFoundExceptionHandler(SysUserNotFoundException e) {
        return new HttpResult.Builder<String>(UserResultStatus.USER_NOT_FOUND)
                .msg(e.getMessage()).build();
    }

}
