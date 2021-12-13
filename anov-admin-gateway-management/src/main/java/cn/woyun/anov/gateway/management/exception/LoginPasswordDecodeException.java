package cn.woyun.anov.gateway.management.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 登录密码解密异常。
 *
 * @author xuepeng
 */
public class LoginPasswordDecodeException extends BaseException {

    /**
     * 构造函数。
     */
    public LoginPasswordDecodeException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public LoginPasswordDecodeException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public LoginPasswordDecodeException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public LoginPasswordDecodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
