package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏请求时间区间错误异常。
 *
 * @author xuepeng
 */
public class DevScreenRequestTimeRangeException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenRequestTimeRangeException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenRequestTimeRangeException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenRequestTimeRangeException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenRequestTimeRangeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
