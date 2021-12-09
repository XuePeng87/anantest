package cn.woyun.anov.gateway.dev.exception.screen;

import cn.woyun.anov.exception.BaseException;

/**
 * 大屏上传图片异常。
 *
 * @author xuepeng
 */
public class DevScreenUploadImgException extends BaseException {

    /**
     * 构造函数。
     */
    public DevScreenUploadImgException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public DevScreenUploadImgException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public DevScreenUploadImgException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public DevScreenUploadImgException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
