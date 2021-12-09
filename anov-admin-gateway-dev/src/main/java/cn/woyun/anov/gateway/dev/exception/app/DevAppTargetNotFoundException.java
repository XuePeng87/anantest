package cn.woyun.anov.gateway.dev.exception.app;

import cn.woyun.anov.exception.BaseException;

/**
 * 要连接的大屏不存在异常。
 *
 * @author xuepeng
 */
public class DevAppTargetNotFoundException extends BaseException {

    /**
     * 构造函数。
     */
    public DevAppTargetNotFoundException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevAppTargetNotFoundException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevAppTargetNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevAppTargetNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
