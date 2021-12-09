package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 用户帐号已存在的异常类。
 *
 * @author xuepeng
 */
public class SysUserAccountDuplicateException extends BaseException {

    /**
     * 构造函数。
     */
    public SysUserAccountDuplicateException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysUserAccountDuplicateException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysUserAccountDuplicateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysUserAccountDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
