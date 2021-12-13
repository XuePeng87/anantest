package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.gateway.management.enums.TenantResultStatus;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.exception.SysTenantEmailDuplicateException;
import cn.woyun.anov.sdk.mgt.exception.SysTenantPhoneDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 租户的异常处理。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class TenantExceptionHandlerConfig {

    /**
     * 租户电话号已存在。
     */
    @ExceptionHandler(value = SysTenantPhoneDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysTenantPhoneDuplicateExceptionHandler(SysTenantPhoneDuplicateException e) {
        return new HttpResult.Builder<String>(TenantResultStatus.PHONE_DUPLICATE)
                .msg(e.getMessage()).build();
    }

    /**
     * 租户邮箱已存在。
     */
    @ExceptionHandler(value = SysTenantEmailDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysTenantEmailDuplicateExceptionHandler(SysTenantEmailDuplicateException e) {
        return new HttpResult.Builder<String>(TenantResultStatus.EMAIL_DUPLICATE)
                .msg(e.getMessage()).build();
    }

}
