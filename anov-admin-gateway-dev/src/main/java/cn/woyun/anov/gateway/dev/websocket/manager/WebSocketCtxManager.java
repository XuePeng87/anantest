package cn.woyun.anov.gateway.dev.websocket.manager;

/**
 * WebSocket连接管理器。
 *
 * @author xuepeng
 */
public interface WebSocketCtxManager {

    /**
     * 将连接添加到管理器中。
     *
     * @param ctx WebSocketSession对象。
     */
    void addCtx(final WebSocketCtx ctx);

    /**
     * 将连接从管理中移除。
     *
     * @param ctx WebSocketSession对象。
     */
    void removeCtx(final WebSocketCtx ctx);

    /**
     * 从管理器中获取连接。
     *
     * @param code 客户端唯一标识。
     * @return 连接。
     */
    WebSocketCtx findByCode(final String code);

    /**
     * @return 获取当前在线数。
     */
    int getOnlineCount();

}
