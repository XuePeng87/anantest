package cn.woyun.anov.gateway.dev.exception.app;

import cn.woyun.anov.exception.BaseException;

/**
 * 设备登录用户名或密码不正确异常。
 *
 * @author xuepeng
 */
public class DevAppLoginFailedException extends BaseException {

    /**
     * 构造函数。
     */
    public DevAppLoginFailedException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevAppLoginFailedException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevAppLoginFailedException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevAppLoginFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
