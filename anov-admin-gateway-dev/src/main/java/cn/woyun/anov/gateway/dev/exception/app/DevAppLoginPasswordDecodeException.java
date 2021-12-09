package cn.woyun.anov.gateway.dev.exception.app;

import cn.woyun.anov.exception.BaseException;

/**
 * 设备登录密码解密异常。
 *
 * @author xuepeng
 */
public class DevAppLoginPasswordDecodeException extends BaseException {

    /**
     * 构造函数。
     */
    public DevAppLoginPasswordDecodeException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevAppLoginPasswordDecodeException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevAppLoginPasswordDecodeException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevAppLoginPasswordDecodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
