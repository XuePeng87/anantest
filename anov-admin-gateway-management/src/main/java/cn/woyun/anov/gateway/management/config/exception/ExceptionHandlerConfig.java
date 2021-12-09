package cn.woyun.anov.gateway.management.config.exception;

import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.dev.exception.DevScreenCannotCreateException;
import cn.woyun.anov.sdk.mgt.exception.*;
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
     * SysTenantPhoneDuplicateException的处理方法。
     *
     * @param e SysTenantPhoneDuplicateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysTenantPhoneDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysTenantPhoneDuplicateExceptionHandler(SysTenantPhoneDuplicateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("租户电话号已存在。", e.getMessage());
    }

    /**
     * SysTenantEmailDuplicateException的处理方法。
     *
     * @param e SysTenantEmailDuplicateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysTenantEmailDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysTenantEmailDuplicateExceptionHandler(SysTenantEmailDuplicateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("租户邮箱已存在。", e.getMessage());
    }

    /**
     * SysUserNotFoundException的处理方法。
     *
     * @param e SysUserNotFoundException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysUserNotFoundException.class)
    @ResponseBody
    public HttpResult<String> sysUserNotFoundExceptionExceptionHandler(SysUserNotFoundException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("没有找到用户。", e.getMessage());
    }

    /**
     * SysUserAccountDuplicateException的处理方法。
     *
     * @param e SysUserAccountDuplicateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysUserAccountDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysUserAccountDuplicateExceptionHandler(SysUserAccountDuplicateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("用户帐号已存在。", e.getMessage());
    }

    /**
     * SysUserPhoneDuplicateException的处理方法。
     *
     * @param e SysUserPhoneDuplicateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysUserPhoneDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysUserPhoneDuplicateExceptionHandler(SysUserPhoneDuplicateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("用户电话号已存在。", e.getMessage());
    }

    /**
     * SysUserEmailDuplicateException的处理方法。
     *
     * @param e SysUserEmailDuplicateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysUserEmailDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysUserEmailDuplicateExceptionHandler(SysUserEmailDuplicateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("用户邮箱已存在。", e.getMessage());
    }

    /**
     * SysUserOldPasswordIncorrectException的处理方法。
     *
     * @param e SysUserOldPasswordIncorrectException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysUserOldPasswordIncorrectException.class)
    @ResponseBody
    public HttpResult<String> sysUserOldPasswordIncorrectExceptionHandler(SysUserOldPasswordIncorrectException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("旧密码不正确。", e.getMessage());
    }

    /**
     * SysDeptCannotDeleteException的处理方法。
     *
     * @param e SysDeptCannotDeleteException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysDeptCannotDeleteException.class)
    @ResponseBody
    public HttpResult<String> sysDeptCannotDeleteExceptionHandler(SysDeptCannotDeleteException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("部门下存在用户，无法删除。", e.getMessage());
    }

    /**
     * SysFuncNameDuplicateException的处理方法。
     *
     * @param e SysFuncNameDuplicateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysFuncNameDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysFuncNameDuplicateExceptionHandler(SysFuncNameDuplicateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("功能名称已存在。", e.getMessage());
    }

    /**
     * SysFuncComponentNameDuplicateException的处理方法。
     *
     * @param e SysFuncComponentNameDuplicateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysFuncComponentNameDuplicateException.class)
    @ResponseBody
    public HttpResult<String> sysFuncComponentNameDuplicateExceptionHandler(SysFuncComponentNameDuplicateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("功能组件已存在。", e.getMessage());
    }

    /**
     * SysFuncCannotDeleteException的处理方法。
     *
     * @param e SysFuncCannotDeleteException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysFuncCannotDeleteException.class)
    @ResponseBody
    public HttpResult<String> sysFuncCannotDeleteExceptionHandler(SysFuncCannotDeleteException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("功能无法删除。", e.getMessage());
    }

    /**
     * SysRoleCannotDeleteException的处理方法。
     *
     * @param e SysRoleCannotDeleteException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = SysRoleCannotDeleteException.class)
    @ResponseBody
    public HttpResult<String> sysRoleCannotDeleteExceptionHandler(SysRoleCannotDeleteException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("角色无法删除。", e.getMessage());
    }

    /**
     * DevScreenCannotCreateException的处理方法。
     *
     * @param e DevScreenCannotCreateException。
     * @return 错误信息。
     */
    @ExceptionHandler(value = DevScreenCannotCreateException.class)
    @ResponseBody
    public HttpResult<String> devScreenCannotCreateExceptionHandler(DevScreenCannotCreateException e) {
        log.error(e.getMessage());
        return DefaultHttpResultFactory.fail("大屏无法创建。", e.getMessage());
    }


}
