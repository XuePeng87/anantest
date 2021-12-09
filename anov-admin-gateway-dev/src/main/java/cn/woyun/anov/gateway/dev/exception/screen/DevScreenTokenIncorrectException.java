package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏token非法异常。
 *
 * @author xuepeng
 */
public class DevScreenTokenIncorrectException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenTokenIncorrectException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenTokenIncorrectException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenTokenIncorrectException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenTokenIncorrectException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
