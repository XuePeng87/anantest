package cn.woyun.anov.gateway.management.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 系统的自定义返回码。
 *
 * @author xuepeng
 */
public enum SystemResultStatus implements HttpResultStatus {

    /**
     * 密码解密失败。
     */
    PASSWORD_DECODE_FAILED(50001, "密码解密失败。"),

    /**
     * 验证码过期或不存在。
     */
    VERIFY_CODE_EXPIRED(50002, "验证码过期或不存在。"),

    /**
     * 验证码不正确。
     */
    VERIFY_CODE_INCORREC(50003, "验证码不正确。"),

    /**
     * 验证码不正确。
     */
    LOGIN_FAILED(50004, "用户名或密码不正确。"),

    /**
     * 未登录系统。
     */
    NOT_LOGIN(60001, "未登录系统");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    SystemResultStatus(
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
