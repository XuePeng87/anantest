package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏token格式异常。
 *
 * @author xuepeng
 */
public class DevScreenTokenFormatException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenTokenFormatException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenTokenFormatException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenTokenFormatException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenTokenFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
