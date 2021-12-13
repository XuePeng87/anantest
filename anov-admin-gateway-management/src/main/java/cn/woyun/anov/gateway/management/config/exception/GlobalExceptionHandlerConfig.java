package cn.woyun.anov.gateway.management.config.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.woyun.anov.gateway.management.enums.SystemResultStatus;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的异常统一处理类。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerConfig {

    /**
     * SpringBoot接口参数验证失败的异常处理。
     *
     * @param e MethodArgumentNotValidException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public HttpResult<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        final Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return DefaultHttpResultFactory.param("接口参数验证失败。", errors);
    }

    /**
     * 未登录系统
     */
    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public HttpResult<String> notLoginExceptionHandler(NotLoginException e) {
        return new HttpResult.Builder<String>(SystemResultStatus.NOT_LOGIN)
                .msg(e.getMessage()).build();
    }

}
