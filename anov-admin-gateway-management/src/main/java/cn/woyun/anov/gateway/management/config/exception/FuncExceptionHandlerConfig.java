package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.gateway.management.enums.FuncResultStatus;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.exception.SysFuncCannotDeleteException;
import cn.woyun.anov.sdk.mgt.exception.SysFuncComponentNameDuplicateException;
import cn.woyun.anov.sdk.mgt.exception.SysFuncNameDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能的异常处理。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class FuncExceptionHandlerConfig {

    /**
     * 功能名称已存在。
     */
    @ExceptionHandler(value = SysFuncNameDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysFuncNameDuplicateExceptionHandler(SysFuncNameDuplicateException e) {
        return new HttpResult.Builder<String>(FuncResultStatus.NAME_DUPLICATE)
                .msg(e.getMessage()).build();
    }

    /**
     * 功能组件名称已存在。
     */
    @ExceptionHandler(value = SysFuncComponentNameDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysFuncComponentNameDuplicateExceptionHandler(SysFuncComponentNameDuplicateException e) {
        return new HttpResult.Builder<String>(FuncResultStatus.COMPONENT_NAME_DUPLICATE)
                .msg(e.getMessage()).build();
    }

    /**
     * 功能无法删除。
     */
    @ExceptionHandler(value = SysFuncCannotDeleteException.class)
    @ResponseBody
    public HttpResult<String> sysFuncCannotDeleteExceptionHandler(SysFuncCannotDeleteException e) {
        return new HttpResult.Builder<String>(FuncResultStatus.CAN_NOT_DELETE)
                .msg(e.getMessage()).build();
    }

}
