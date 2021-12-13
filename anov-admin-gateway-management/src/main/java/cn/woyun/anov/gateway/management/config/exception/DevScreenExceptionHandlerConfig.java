package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.gateway.management.enums.DevScreenResultStatus;
import cn.woyun.anov.gateway.management.enums.FuncResultStatus;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.dev.exception.DevScreenCannotCreateException;
import cn.woyun.anov.sdk.dev.exception.DevScreenClientQuitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理类。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class DevScreenExceptionHandlerConfig {

    /**
     * 大屏无法创建。
     */
    @ExceptionHandler(value = DevScreenCannotCreateException.class)
    @ResponseBody
    public HttpResult<String> devScreenCannotCreateExceptionHandler(DevScreenCannotCreateException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.CAN_NOT_CREATE)
                .msg(e.getMessage()).build();
    }

    /**
     * 大屏客户端强制退出失败。
     */
    @ExceptionHandler(value = DevScreenClientQuitException.class)
    @ResponseBody
    public HttpResult<String> devScreenClientQuitExceptionHandler(DevScreenClientQuitException e) {
        return new HttpResult.Builder<String>(DevScreenResultStatus.QUIT_FAILED)
                .msg(e.getMessage()).build();
    }

}
