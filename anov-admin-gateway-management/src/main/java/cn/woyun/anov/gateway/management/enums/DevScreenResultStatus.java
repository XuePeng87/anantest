package cn.woyun.anov.gateway.management.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 大屏的自定义返回码。
 *
 * @author xuepeng
 */
public enum DevScreenResultStatus implements HttpResultStatus {

    /**
     * 大屏无法创建。
     */
    CAN_NOT_CREATE(50601, "大屏无法创建。"),

    /**
     * 强制退出大屏客户端失败。
     */
    QUIT_FAILED(50602, "强制退出大屏失败。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    DevScreenResultStatus(
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
