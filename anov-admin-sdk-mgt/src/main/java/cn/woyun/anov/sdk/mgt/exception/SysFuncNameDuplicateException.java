package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 功能名称已存在的异常类。
 *
 * @author xuepeng
 */
public class SysFuncNameDuplicateException extends BaseException {

    /**
     * 构造函数。
     */
    public SysFuncNameDuplicateException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysFuncNameDuplicateException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysFuncNameDuplicateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysFuncNameDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
