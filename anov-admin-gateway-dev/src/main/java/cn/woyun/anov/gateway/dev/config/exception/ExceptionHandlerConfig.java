package cn.woyun.anov.gateway.dev.config.exception;

import cn.woyun.anov.gateway.dev.enums.DevScreenResultStatus;
import cn.woyun.anov.gateway.dev.exception.app.*;
import cn.woyun.anov.gateway.dev.exception.screen.*;
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
 * 异常统一处理类。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {

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
     * 大屏Token解析失败。
     */
    @ExceptionHandler(value = DevScreenTokenDecodeException.class)
    @ResponseBody
    public HttpResult<String> devScreenTokenDecodeExceptionHandler(DevScreenTokenDecodeException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_TOKEN_DECODE)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏Token格式化失败。
     */
    @ExceptionHandler(value = DevScreenTokenFormatException.class)
    @ResponseBody
    public HttpResult<String> devScreenTokenFormatExceptionHandler(DevScreenTokenFormatException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_TOKEN_FORMAT)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏Token不正确。
     */
    @ExceptionHandler(value = DevScreenTokenIncorrectException.class)
    @ResponseBody
    public HttpResult<String> devScreenTokenIncorrectExceptionHandler(DevScreenTokenIncorrectException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_TOKEN_INCORREC)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏请求时间不正确。
     */
    @ExceptionHandler(value = DevScreenRequestTimeRangeException.class)
    @ResponseBody
    public HttpResult<String> devScreenRequestTimeRangeExceptionHandler(DevScreenRequestTimeRangeException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_REQUEST_TIME_RANGE)
                .msg(e.getMessage()).build();
    }

    /**
     * 验证的大屏不存在。
     */
    @ExceptionHandler(value = DevScreenNotFoundException.class)
    @ResponseBody
    public HttpResult<String> devScreenNotFoundExceptionHandler(DevScreenNotFoundException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_NOT_FOUND)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏有效期已过期。
     */
    @ExceptionHandler(value = DevScreenExpireException.class)
    @ResponseBody
    public HttpResult<String> devScreenExpireExceptionHandler(DevScreenExpireException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_EXPIRE)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏状态为不可用。
     */
    @ExceptionHandler(value = DevScreenStatusException.class)
    @ResponseBody
    public HttpResult<String> devScreenStatusExceptionHandler(DevScreenStatusException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_STATUS)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏监控图片上传失败。
     */
    @ExceptionHandler(value = DevScreenUploadImgException.class)
    @ResponseBody
    public HttpResult<String> devScreenUploadImgExceptionHandler(DevScreenUploadImgException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_SCREEN_PIC_UPLOAD)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器登录密码解析失败。
     */
    @ExceptionHandler(value = DevAppLoginPasswordDecodeException.class)
    @ResponseBody
    public HttpResult<String> devAppLoginPasswordDecodeExceptionHandler(DevAppLoginPasswordDecodeException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_APP_PASSWORD_DECODE)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器登录时验证码不正确。
     */
    @ExceptionHandler(value = DevAppLoginVerifyCodeException.class)
    @ResponseBody
    public HttpResult<String> devScreenTokenDecodeExceptionHandler(DevAppLoginVerifyCodeException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_APP_VERIFY_CODE_INCORREC)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器登录时用户名或密码错误。
     */
    @ExceptionHandler(value = DevAppLoginFailedException.class)
    @ResponseBody
    public HttpResult<String> devAppLoginFailedExceptionHandler(DevAppLoginFailedException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_APP_LOGIN_FAILED)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器控制的目标不存在
     */
    @ExceptionHandler(value = DevAppTargetNotFoundException.class)
    @ResponseBody
    public HttpResult<String> devAppTargetNotFoundExceptionHandler(DevAppTargetNotFoundException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_APP_TARGET_NOT_FOUND)
                .msg(e.getMessage()).build();
    }

    /**
     * 控制器控制的目标未上线
     */
    @ExceptionHandler(value = DevAppTargetOfflineException.class)
    @ResponseBody
    public HttpResult<String> devAppTargetOfflineExceptionHandler(DevAppTargetOfflineException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.DEV_APP_TARGET_OFFLINE)
                .msg(e.getMessage()).build();
    }

}
