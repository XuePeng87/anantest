package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.gateway.management.enums.DeptResultStatus;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.exception.SysDeptCannotDeleteException;
import cn.woyun.anov.sdk.mgt.exception.SysRoleCannotDeleteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 部门的异常处理。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class DeptExceptionHandlerConfig {

    /**
     * 部门无法删除。
     */
    @ExceptionHandler(value = SysDeptCannotDeleteException.class)
    @ResponseBody
    public HttpResult<String> sysRoleCannotDeleteExceptionHandler(SysRoleCannotDeleteException e) {
        return new HttpResult.Builder<String>(DeptResultStatus.CAN_NOT_DELETE)
                .msg(e.getMessage()).build();
    }

}
