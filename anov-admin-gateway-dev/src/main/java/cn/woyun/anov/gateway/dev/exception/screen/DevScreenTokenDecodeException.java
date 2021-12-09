package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏token解密异常。
 *
 * @author xuepeng
 */
public class DevScreenTokenDecodeException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenTokenDecodeException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenTokenDecodeException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenTokenDecodeException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenTokenDecodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
