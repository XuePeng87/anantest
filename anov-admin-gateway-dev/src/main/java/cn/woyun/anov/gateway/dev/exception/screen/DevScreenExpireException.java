package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏有效期过期异常。
 *
 * @author xuepeng
 */
public class DevScreenExpireException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenExpireException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenExpireException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenExpireException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenExpireException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
