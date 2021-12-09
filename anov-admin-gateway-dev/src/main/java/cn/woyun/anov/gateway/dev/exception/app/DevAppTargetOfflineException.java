package cn.woyun.anov.gateway.dev.exception.app;

import cn.woyun.anov.exception.BaseException;

/**
 * 要连接的大屏未上线。
 *
 * @author xuepeng
 */
public class DevAppTargetOfflineException extends BaseException {

    /**
     * 构造函数。
     */
    public DevAppTargetOfflineException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevAppTargetOfflineException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevAppTargetOfflineException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevAppTargetOfflineException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
