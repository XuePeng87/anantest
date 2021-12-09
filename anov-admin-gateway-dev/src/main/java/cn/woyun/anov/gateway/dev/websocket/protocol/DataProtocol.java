package cn.woyun.anov.gateway.dev.websocket.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 协议数据。
 *
 * @author xuepeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProtocol {

    /**
     * 命令。
     */
    private String command;

    /**
     * 设备类型。
     */
    private String device;

    /**
     * 项目标识。
     */
    private String key;

    /**
     * 客户端唯一标识。
     */
    private String code;

    /**
     * 目标客户端唯一标识。
     */
    private String target;

    /**
     * 同步消息
     */
    private JsonNode message;

    /**
     * 日志类型。
     */
    private String logType;

    /**
     * 日志模块
     */
    private String logModule;
    
    /**
     * 日志内容。
     */
    private String logDesc;

}
