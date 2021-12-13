package cn.woyun.anov.gateway.dev.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * APP API的自定义返回码。
 *
 * @author xuepeng
 */
public enum AppResultStatus implements HttpResultStatus {

    /**
     * 密码解密错误。
     */
    PASSWORD_DECODE(51001, "登录密码无法解密。"),

    /**
     * 验证码过期或不存在。
     */
    VERIFY_CODE_EXPIRED(51002, "验证码过期或不存在。"),

    /**
     * 验证码不正确。
     */
    VERIFY_CODE_INCORREC(51003, "验证码不正确。"),

    /**
     * 用户名或密码不正确。
     */
    LOGIN_FAILED(51004, "用户名或密码不正确。"),

    /**
     * 要控制的大屏不存在。
     */
    TARGET_NOT_FOUND(51005, "要控制的大屏不存在。"),

    /**
     * 要控制的大屏未上线。
     */
    TARGET_OFFLINE(51006, "要控制的大屏未上线。"),

    /**
     * 未登录系统。
     */
    NOT_LOGIN(61001, "未登录系统");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    AppResultStatus(
            final int code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @return 获得状态编码。
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * @return 获得状态描述。
     */
    @Override
    public String getDesc() {
        return desc;
    }

    /**
     * 状态编码。
     */
    private final int code;
    /**
     * 状态描述。
     */
    private final String desc;

}
