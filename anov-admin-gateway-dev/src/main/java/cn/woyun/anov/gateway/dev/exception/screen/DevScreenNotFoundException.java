package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏不存在异常。
 *
 * @author xuepeng
 */
public class DevScreenNotFoundException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenNotFoundException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenNotFoundException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
