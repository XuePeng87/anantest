package cn.woyun.anov.sdk.dev.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏无法创建的异常。
 *
 * @author xuepeng
 */
public class DevScreenCannotCreateException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenCannotCreateException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenCannotCreateException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenCannotCreateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenCannotCreateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
