package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏不可用异常。
 *
 * @author xuepeng
 */
public class DevScreenStatusException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenStatusException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenStatusException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenStatusException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenStatusException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
