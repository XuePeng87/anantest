package cn.woyun.anov.gateway.dev.websocket.protocol;

import lombok.Data;

/**
 * 大屏WebSocket协议。
 *
 * @author xuepeng
 */
@Data
public class DevWebSocketProtocol {

    /**
     * 协议类型。
     */
    private String type;

    /**
     * 是否需要确认。
     */
    private Boolean confirm;

    /**
     * 协议数据。
     */
    private DataProtocol data;

}
