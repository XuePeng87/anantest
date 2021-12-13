package cn.woyun.anov.gateway.management.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 用户名或密码不正确时发生异常。
 *
 * @author xuepeng
 */
public class LoginFailedException extends BaseException {

    /**
     * 构造函数。
     */
    public LoginFailedException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public LoginFailedException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public LoginFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
