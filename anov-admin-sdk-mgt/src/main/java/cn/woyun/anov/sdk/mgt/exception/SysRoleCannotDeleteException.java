package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 角色无法删除的异常类。
 *
 * @author xuepeng
 */
public class SysRoleCannotDeleteException extends BaseException {

    /**
     * 构造函数。
     */
    public SysRoleCannotDeleteException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysRoleCannotDeleteException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysRoleCannotDeleteException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysRoleCannotDeleteException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
