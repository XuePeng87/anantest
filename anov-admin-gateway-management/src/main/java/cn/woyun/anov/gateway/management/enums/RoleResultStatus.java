package cn.woyun.anov.gateway.management.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 角色的自定义返回码。
 *
 * @author xuepeng
 */
public enum RoleResultStatus implements HttpResultStatus {

    /**
     * 角色编号已存在。
     */
    CODE_DUPLICATE(50301, "角色编号已存在。"),

    /**
     * 角色名称已存在。
     */
    PHONE_DUPLICATE(50302, "角色名称已存在。"),

    /**
     * 角色无法删除。
     */
    CAN_NOT_DELETE(50303, "角色无法删除。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    RoleResultStatus(
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
