package cn.woyun.anov.gateway.dev.config.exception;

import cn.woyun.anov.gateway.dev.enums.ScreenResultStatus;
import cn.woyun.anov.gateway.dev.exception.screen.*;
import cn.woyun.anov.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Screen异常统一处理类。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class ScreenExceptionHandlerConfig {

    /**
     * 大屏Token解析失败。
     */
    @ExceptionHandler(value = DevScreenTokenDecodeException.class)
    @ResponseBody
    public HttpResult<String> devScreenTokenDecodeExceptionHandler(DevScreenTokenDecodeException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.TOKEN_DECODE)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏Token格式化失败。
     */
    @ExceptionHandler(value = DevScreenTokenFormatException.class)
    @ResponseBody
    public HttpResult<String> devScreenTokenFormatExceptionHandler(DevScreenTokenFormatException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.TOKEN_FORMAT)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏Token不正确。
     */
    @ExceptionHandler(value = DevScreenTokenIncorrectException.class)
    @ResponseBody
    public HttpResult<String> devScreenTokenIncorrectExceptionHandler(DevScreenTokenIncorrectException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.TOKEN_INCORREC)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏请求时间不正确。
     */
    @ExceptionHandler(value = DevScreenRequestTimeRangeException.class)
    @ResponseBody
    public HttpResult<String> devScreenRequestTimeRangeExceptionHandler(DevScreenRequestTimeRangeException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.REQUEST_TIME_RANGE)
                .msg(e.getMessage()).build();
    }

    /**
     * 验证的大屏不存在。
     */
    @ExceptionHandler(value = DevScreenNotFoundException.class)
    @ResponseBody
    public HttpResult<String> devScreenNotFoundExceptionHandler(DevScreenNotFoundException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.SCREEN_NOT_FOUND)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏有效期已过期。
     */
    @ExceptionHandler(value = DevScreenExpireException.class)
    @ResponseBody
    public HttpResult<String> devScreenExpireExceptionHandler(DevScreenExpireException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.SCREEN_EXPIRE)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏状态为不可用。
     */
    @ExceptionHandler(value = DevScreenStatusException.class)
    @ResponseBody
    public HttpResult<String> devScreenStatusExceptionHandler(DevScreenStatusException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.SCREEN_STATUS)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏监控图片上传失败。
     */
    @ExceptionHandler(value = DevScreenUploadImgException.class)
    @ResponseBody
    public HttpResult<String> devScreenUploadImgExceptionHandler(DevScreenUploadImgException e) {
        return new HttpResult.Builder<String>(ScreenResultStatus.SCREEN_PIC_UPLOAD)
                .msg(e.getMessage()).build();
    }

}
