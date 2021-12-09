package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 租户邮箱已存在的异常类。
 *
 * @author xuepeng
 */
public class SysTenantEmailDuplicateException extends BaseException {

    /**
     * 构造函数。
     */
    public SysTenantEmailDuplicateException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysTenantEmailDuplicateException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysTenantEmailDuplicateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysTenantEmailDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
