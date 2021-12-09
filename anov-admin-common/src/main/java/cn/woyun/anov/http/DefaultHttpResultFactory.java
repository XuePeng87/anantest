package cn.woyun.anov.http;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpResult的简单工厂。
 * 用来提供Framework提供的默认HttpResult对象。
 *
 * @author xuepeng
 */
public class DefaultHttpResultFactory {

    /**
     * 构造函数。
     */
    private DefaultHttpResultFactory() {
    }

    /**
     * 默认的Data数据结构。
     */
    private static final Map<String, String> DEFAULT_DATA = new HashMap<>();

    /**
     * 创建成功的HttpResult对象。
     *
     * @return 成功的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> success() {
        return success(StringUtils.EMPTY);
    }

    /**
     * 创建成功的HttpResult对象。
     *
     * @param msg 返回的消息。
     * @return 成功的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> success(final String msg) {
        return new HttpResult.Builder<Map<String, String>>(DefaultHttpResultStatus.SUCCESS)
                .msg(msg)
                .data(DEFAULT_DATA).build();
    }

    /**
     * 创建成功的HttpResult对象。
     *
     * @param msg  返回的消息。
     * @param data 返回的数据。
     * @return 成功的HttpResult对象。
     */
    public static <T> HttpResult<T> success(final String msg, final T data) {
        return new HttpResult.Builder<T>(DefaultHttpResultStatus.SUCCESS).msg(msg).data(data).build();
    }

    /**
     * 创建超时的HttpResult对象。
     *
     * @return 超时的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> timeout() {
        return timeout(StringUtils.EMPTY);
    }

    /**
     * 创建超时的HttpResult对象。
     *
     * @param msg 返回的消息。
     * @return 超时的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> timeout(final String msg) {
        return new HttpResult.Builder<Map<String, String>>(DefaultHttpResultStatus.TIMEOUT)
                .msg(msg)
                .data(DEFAULT_DATA).build();
    }

    /**
     * 创建超时的HttpResult对象。
     *
     * @param msg  返回的消息。
     * @param data 返回的数据。
     * @return 超时的HttpResult对象。
     */
    public static <T> HttpResult<T> timeout(final String msg, final T data) {
        return new HttpResult.Builder<T>(DefaultHttpResultStatus.TIMEOUT).msg(msg).data(data).build();
    }

    /**
     * 创建参数错误的HttpResult对象。
     *
     * @return 参数错误的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> param() {
        return param(StringUtils.EMPTY);
    }

    /**
     * 创建参数错误的HttpResult对象。
     *
     * @param msg 返回的消息。
     * @return 参数错误的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> param(final String msg) {
        return new HttpResult.Builder<Map<String, String>>(DefaultHttpResultStatus.PARAM)
                .msg(msg)
                .data(DEFAULT_DATA).build();
    }

    /**
     * 创建参数错误的HttpResult对象。
     *
     * @param msg  返回的消息。
     * @param data 返回的数据。
     * @return 参数错误的HttpResult对象。
     */
    public static <T> HttpResult<T> param(final String msg, final T data) {
        return new HttpResult.Builder<T>(DefaultHttpResultStatus.PARAM).msg(msg).data(data).build();
    }


    /**
     * 创建失败的HttpResult对象。
     *
     * @return 失败的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> fail() {
        return fail(StringUtils.EMPTY);
    }

    /**
     * 创建失败的HttpResult对象。
     *
     * @param msg 返回的消息。
     * @return 失败的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> fail(final String msg) {
        return new HttpResult.Builder<Map<String, String>>(DefaultHttpResultStatus.FAIL)
                .msg(msg)
                .data(DEFAULT_DATA).build();
    }

    /**
     * 创建失败的HttpResult对象。
     *
     * @param msg  返回的消息。
     * @param data 返回的数据。
     * @return 失败的HttpResult对象。
     */
    public static <T> HttpResult<T> fail(final String msg, final T data) {
        return new HttpResult.Builder<T>(DefaultHttpResultStatus.FAIL).msg(msg).data(data).build();
    }

    /**
     * 创建授权失败的HttpResult对象。
     *
     * @return 授权失败的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> permissions() {
        return permissions(StringUtils.EMPTY);
    }

    /**
     * 创建授权失败的HttpResult对象。
     *
     * @param msg 返回的消息。
     * @return 授权失败的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> permissions(final String msg) {
        return new HttpResult.Builder<Map<String, String>>(DefaultHttpResultStatus.PERMISSIONS)
                .msg(msg)
                .data(DEFAULT_DATA).build();
    }

    /**
     * 创建授权失败的HttpResult对象。
     *
     * @param msg  返回的消息。
     * @param data 返回的数据。
     * @return 授权失败的HttpResult对象。
     */
    public static <T> HttpResult<T> permissions(final String msg, final T data) {
        return new HttpResult.Builder<T>(DefaultHttpResultStatus.PERMISSIONS).msg(msg).data(data).build();
    }

    /**
     * 创建系统异常的HttpResult对象。
     *
     * @return 系统异常的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> error() {
        return error(StringUtils.EMPTY);
    }

    /**
     * 创建系统异常的HttpResult对象。
     *
     * @param msg 返回的消息。
     * @return 系统异常的HttpResult对象。
     */
    public static HttpResult<Map<String, String>> error(final String msg) {
        return new HttpResult.Builder<Map<String, String>>(DefaultHttpResultStatus.ERROR)
                .msg(msg)
                .data(DEFAULT_DATA).build();
    }

    /**
     * 创建系统异常的HttpResult对象。
     *
     * @param msg  返回的消息。
     * @param data 返回的数据。
     * @return 系统异常的HttpResult对象。
     */
    public static <T> HttpResult<T> error(final String msg, final T data) {
        return new HttpResult.Builder<T>(DefaultHttpResultStatus.ERROR).msg(msg).data(data).build();
    }

}
