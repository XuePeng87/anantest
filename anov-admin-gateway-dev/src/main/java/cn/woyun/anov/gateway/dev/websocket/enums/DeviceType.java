package cn.woyun.anov.gateway.dev.websocket.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 新能源GB32960协议的命令枚举。
 *
 * @author xuepeng
 */
public enum DeviceType {

    /**
     * 移动端。
     */
    APP("app", "移动端"),

    /**
     * 平板端。
     */
    PAD("pad", "平板端"),

    /**
     * 电脑端。
     */
    PC("pc", "电脑端");

    /**
     * 根据ID获取枚举。
     *
     * @param id 类型ID。
     * @return 枚举。
     */
    public static DeviceType findById(final String id) {
        for (DeviceType enums : DeviceType.values()) {
            if (StringUtils.equals(enums.getId(), id)) {
                return enums;
            }
        }
        return null;
    }

    /**
     * 构造函数。
     *
     * @param id   命令ID。
     * @param desc 命令描述。
     */
    DeviceType(final String id, final String desc) {
        this.id = id;
        this.desc = desc;
    }

    /**
     * @return 获取命令ID。
     */
    public String getId() {
        return id;
    }

    /**
     * @return 获取命令描述。
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 命令ID。
     */
    private final String id;

    /**
     * 命令描述。
     */
    private final String desc;

}
