package cn.woyun.anov.gateway.management.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 用户的自定义返回码。
 *
 * @author xuepeng
 */
public enum UserResultStatus implements HttpResultStatus {

    /**
     * 用户帐号已存在。
     */
    ACCOUNT_DUPLICATE(50201, "用户帐号已存在。"),

    /**
     * 用户电话号已存在。
     */
    PHONE_DUPLICATE(50202, "用户电话号已存在。"),

    /**
     * 用户邮箱已存在。
     */
    EMAIL_DUPLICATE(50203, "用户邮箱已存在。"),

    /**
     * 旧密码错误。
     */
    OLD_PASSWORD_INCORRECT(50204, "旧密码错误。"),

    /**
     * 用户不存在。
     */
    USER_NOT_FOUND(50205, "用户不存在。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    UserResultStatus(
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
