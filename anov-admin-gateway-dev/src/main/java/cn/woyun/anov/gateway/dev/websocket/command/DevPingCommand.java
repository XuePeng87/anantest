package cn.woyun.anov.gateway.dev.websocket.command;

import cn.woyun.anov.gateway.dev.websocket.enums.CommandType;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtx;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtxManager;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * 心跳命令的业务处理类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class DevPingCommand extends DevAbstractCommand {

    /**
     * 执行命令。
     *
     * @param dataProtocol 数据协议。
     */
    @Override
    public void execute(final DataProtocol dataProtocol) {
        try {
            final WebSocketCtx ctx = webSocketCtxManager.findByCode(dataProtocol.getCode() + dataProtocol.getKey());
            if (!Objects.isNull(ctx)) {
                ctx.getSession().getBasicRemote().sendText("{\"screenCode\":\"ping\"}");
            }
        } catch (IOException e) {
            log.error("server error, cause is {}", e.getMessage());
        }
    }

    /**
     * @return 心跳命令。
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.PING;
    }

    /**
     * 自动装配WebSocket连接管理器。
     *
     * @param webSocketCtxManager WebSocket连接管理器。
     */
    @Autowired
    public void setWebSocketCtxManager(WebSocketCtxManager webSocketCtxManager) {
        this.webSocketCtxManager = webSocketCtxManager;
    }

    /**
     * WebSocket连接管理器。
     */
    private WebSocketCtxManager webSocketCtxManager;

}
