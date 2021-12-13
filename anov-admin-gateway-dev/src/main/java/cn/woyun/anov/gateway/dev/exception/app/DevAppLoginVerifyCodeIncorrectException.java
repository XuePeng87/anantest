package cn.woyun.anov.gateway.dev.exception.app;

import cn.woyun.anov.exception.BaseException;

/**
 * 设备登录验证码错误异常。
 *
 * @author xuepeng
 */
public class DevAppLoginVerifyCodeIncorrectException extends BaseException {

    /**
     * 构造函数。
     */
    public DevAppLoginVerifyCodeIncorrectException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevAppLoginVerifyCodeIncorrectException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevAppLoginVerifyCodeIncorrectException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevAppLoginVerifyCodeIncorrectException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
