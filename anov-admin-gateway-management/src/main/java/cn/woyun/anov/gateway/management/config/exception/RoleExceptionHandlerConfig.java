package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.gateway.management.enums.RoleResultStatus;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.exception.SysRoleCannotDeleteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色的异常处理。
 *
 * @author xuepeng
 */
@ControllerAdvice
@Slf4j
public class RoleExceptionHandlerConfig {

    /**
     * 角色无法删除。
     */
    @ExceptionHandler(value = SysRoleCannotDeleteException.class)
    @ResponseBody
    public HttpResult<String> sysRoleCannotDeleteExceptionHandler(SysRoleCannotDeleteException e) {
        return new HttpResult.Builder<String>(RoleResultStatus.CAN_NOT_DELETE)
                .msg(e.getMessage()).build();
    }

}
