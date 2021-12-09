package cn.woyun.anov.sdk.mgt.exception;

import cn.woyun.anov.exception.BaseException;

/**
 * 部门无法删除的异常类。
 *
 * @author xuepeng
 */
public class SysDeptCannotDeleteException extends BaseException {

    /**
     * 构造函数。
     */
    public SysDeptCannotDeleteException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysDeptCannotDeleteException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysDeptCannotDeleteException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysDeptCannotDeleteException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
