package cn.woyun.anov.gateway.management.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 租户的自定义返回码。
 *
 * @author xuepeng
 */
public enum TenantResultStatus implements HttpResultStatus {

    /**
     * 租户电话号已存在。
     */
    PHONE_DUPLICATE(50101, "租户电话号已存在。"),

    /**
     * 租户邮箱已存在。
     */
    EMAIL_DUPLICATE(50102, "租户邮箱已存在。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    TenantResultStatus(
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
