package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 用户邮箱已存在的异常类。
 *
 * @author xuepeng
 */
public class SysUserEmailDuplicateException extends BaseException {

    /**
     * 构造函数。
     */
    public SysUserEmailDuplicateException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysUserEmailDuplicateException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysUserEmailDuplicateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysUserEmailDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
