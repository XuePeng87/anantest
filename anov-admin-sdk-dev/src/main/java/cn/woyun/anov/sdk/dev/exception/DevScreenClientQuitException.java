package cn.woyun.anov.sdk.dev.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏客户端无法强制退出的异常。
 *
 * @author xuepeng
 */
public class DevScreenClientQuitException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenClientQuitException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenClientQuitException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenClientQuitException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenClientQuitException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
