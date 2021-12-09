package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 用户修改密码时，旧密码错误的异常类。
 *
 * @author xuepeng
 */
public class SysUserOldPasswordIncorrectException extends BaseException {

    /**
     * 构造函数。
     */
    public SysUserOldPasswordIncorrectException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysUserOldPasswordIncorrectException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysUserOldPasswordIncorrectException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysUserOldPasswordIncorrectException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
