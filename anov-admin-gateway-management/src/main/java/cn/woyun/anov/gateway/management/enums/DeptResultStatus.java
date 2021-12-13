package cn.woyun.anov.gateway.management.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 部门的自定义返回码。
 *
 * @author xuepeng
 */
public enum DeptResultStatus implements HttpResultStatus {

    /**
     * 部门无法删除。
     */
    CAN_NOT_DELETE(50401, "部门无法删除。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    DeptResultStatus(
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
