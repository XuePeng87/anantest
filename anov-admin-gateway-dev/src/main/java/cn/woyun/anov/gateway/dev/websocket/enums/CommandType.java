package cn.woyun.anov.gateway.dev.websocket.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 新能源GB32960协议的命令枚举。
 *
 * @author xuepeng
 */
public enum CommandType {

    /**
     * 设备上线命令。
     */
    ONLINE("online", "上线"),

    /**
     * 设备下线命令。
     */
    OFFLINE("offline", "下线"),

    /**
     * 同步信息命令。
     */
    PING("ping", "心跳"),

    /**
     * 设备操作日志命令。
     */
    LOG("log", "日志"),

    /**
     * 强制下线命令。
     */
    SYNC("sync", "同步"),

    /**
     * 强制下线命令。
     */
    QUIT("quit", "强制下线");


    /**
     * 根据ID获取枚举。
     *
     * @param id 类型ID。
     * @return 枚举。
     */
    public static CommandType findById(final String id) {
        for (CommandType enums : CommandType.values()) {
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
    CommandType(final String id, final String desc) {
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
