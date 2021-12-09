package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 租户电话号已存在的异常类。
 *
 * @author xuepeng
 */
public class SysTenantPhoneDuplicateException extends BaseException {

    /**
     * 构造函数。
     */
    public SysTenantPhoneDuplicateException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysTenantPhoneDuplicateException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysTenantPhoneDuplicateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysTenantPhoneDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
