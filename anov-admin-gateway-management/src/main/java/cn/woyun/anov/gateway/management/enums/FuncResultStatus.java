package cn.woyun.anov.gateway.management.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 功能的自定义返回码。
 *
 * @author xuepeng
 */
public enum FuncResultStatus implements HttpResultStatus {

    /**
     * 功能名称已存在。
     */
    NAME_DUPLICATE(50501, "功能名称已存在。"),

    /**
     * 功能无法删除。
     */
    COMPONENT_NAME_DUPLICATE(50502, "功能组件名称已存在。"),

    /**
     * 功能无法删除。
     */
    CAN_NOT_DELETE(50503, "功能无法删除。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    FuncResultStatus(
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
