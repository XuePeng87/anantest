package cn.woyun.anov.exception;

/**
 * 框架提供的ParameterException异常对象。
 * 继承了BaseException，当参数错误时，抛出该异常。
 *
 * @author xuepeng
 */
public class ParameterException extends BaseException {

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public ParameterException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public ParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
