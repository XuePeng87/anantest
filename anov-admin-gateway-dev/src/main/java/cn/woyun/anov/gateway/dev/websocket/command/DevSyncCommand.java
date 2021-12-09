package cn.woyun.anov.gateway.dev.websocket.command;

import cn.woyun.anov.gateway.dev.websocket.enums.CommandType;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtx;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtxManager;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 转发命令的业务处理类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class DevSyncCommand extends DevAbstractCommand {

    /**
     * 执行命令。
     *
     * @param dataProtocol 数据协议。
     */
    @Override
    public void execute(final DataProtocol dataProtocol) {
        try {
            // TODO 性能问题需要解决，并且需要确认，设备给大屏消息是否是点对点，大屏给设备消息是广播
            // 查询要发送的目标
            final List<DevScreenClient> clients =
                    devScreenClientService.findSendTargetByKeyAndCode(dataProtocol.getKey(), dataProtocol.getCode());
            for (final DevScreenClient client : clients) {
                final WebSocketCtx ctx = webSocketCtxManager.findByCode(client.getClientCode() + dataProtocol.getKey());
                // 发送信息
                if (!Objects.isNull(ctx) && BooleanUtils.isTrue(client.getClientOnlined())) {
                    final ObjectNode objectNode = (ObjectNode) dataProtocol.getMessage();
                    objectNode.put("fromCodeId", dataProtocol.getCode());
                    objectNode.put("toCodeId", client.getClientCode());
                    ctx.getSession().getBasicRemote().sendText(objectNode.toString());
                }
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
        return CommandType.SYNC;
    }

    /**
     * 自动装配大屏客户端的业务处理接口。
     *
     * @param devScreenClientService 大屏客户端的业务处理接口。
     */
    @Autowired
    public void setDevScreenClientService(DevScreenClientService devScreenClientService) {
        this.devScreenClientService = devScreenClientService;
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
     * 大屏客户端的业务处理接口。
     */
    private DevScreenClientService devScreenClientService;

    /**
     * WebSocket连接管理器。
     */
    private WebSocketCtxManager webSocketCtxManager;

}
