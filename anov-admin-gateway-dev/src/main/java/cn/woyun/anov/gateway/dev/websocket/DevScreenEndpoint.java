package cn.woyun.anov.gateway.dev.websocket;

import cn.woyun.anov.gateway.dev.websocket.command.DevCommand;
import cn.woyun.anov.gateway.dev.websocket.command.DevCommandFactory;
import cn.woyun.anov.gateway.dev.websocket.enums.CommandType;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtx;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtxManager;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import cn.woyun.anov.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;

/**
 * WebSocket服务端的实现类。
 *
 * @author xuepeng
 */
@ServerEndpoint("/v1/devs/{device}/{key}/{code}/{target}")
@Component
@Slf4j
public class DevScreenEndpoint {

    /**
     * 大屏客户端上线。
     *
     * @param session ws连接。
     * @param device  设备类型。
     * @param key     大屏唯一标识。
     * @param code    客户端唯一标识。
     * @param target  目标客户端唯一标识。
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam(value = "device") String device,
                       @PathParam(value = "key") String key,
                       @PathParam(value = "code") String code,
                       @PathParam(value = "target") String target
    ) {
        // 添加连接到管理器。
        final WebSocketCtx ctx = WebSocketCtx.builder()
                .code(code + key)
                .session(session)
                .controllers(new HashSet<>())
                .build();
        webSocketCtxManager.addCtx(ctx);
        // 客户端上线
        final DevCommand devCommand = devCommandFactory.getInstance(CommandType.ONLINE.getId());
        final DataProtocol dataProtocol = DataProtocol.builder()
                .device(device)
                .key(key)
                .code(code)
                .target(target)
                .build();
        devCommand.execute(dataProtocol);
        // 输出日志
        log.info("client is online. key is {}, code is {}, online count is {}",
                key,
                code,
                webSocketCtxManager.getOnlineCount());
    }

    /**
     * 消息处理时间。
     *
     * @param session 客户端连接。
     * @param message 发送的消息。
     * @param key     大屏唯一标识。
     * @param code    客户端唯一标识。
     */
    @OnMessage
    public void onMessage(Session session,
                          String message,
                          @PathParam(value = "key") String key,
                          @PathParam(value = "code") String code) {
        try {
            final JsonNode protocol = JsonMapper.INSTANCE.getInstance().readTree(message);
            if (protocol.has("screenCode")) {
                final String screenCode = protocol.get("screenCode").asText();
                // 判断是否是心跳命令
                if (StringUtils.equals(screenCode, CommandType.PING.getId())) {
                    final DevCommand devCommand = devCommandFactory.getInstance(CommandType.PING.getId());
                    final DataProtocol dataProtocol = DataProtocol.builder().code(code).build();
                    devCommand.execute(dataProtocol);
                } else if (StringUtils.equals(screenCode, CommandType.LOG.getId())) {
                    final DevCommand devCommand = devCommandFactory.getInstance(CommandType.LOG.getId());
                    final DataProtocol dataProtocol = DataProtocol.builder()
                            .key(key).code(code)
                            .logType(protocol.get("logType").asText())
                            .logDesc(protocol.get("logDesc").asText())
                            .build();
                    devCommand.execute(dataProtocol);
                } else {
                    final DataProtocol dataProtocol = DataProtocol.builder()
                            .key(key).code(code).message(protocol).build();
                    final DevCommand devCommand = devCommandFactory.getInstance(CommandType.SYNC.getId());
                    devCommand.execute(dataProtocol);
                }
            }
        } catch (JsonProcessingException e) {
            log.error("protocol process failed, message is {}", message);
            try {
                session.close();
            } catch (IOException ioException) {
                log.error("server error, cause is {}", e.getMessage());
            }
        }
    }

    /**
     * 大屏客户端断开。
     *
     * @param session ws连接。
     * @param device  设备类型。
     * @param key     大屏唯一标识。
     * @param code    客户端唯一标识。
     * @param target  目标客户端唯一标识。
     */
    @OnClose
    public void onClose(Session session,
                        @PathParam(value = "device") String device,
                        @PathParam(value = "key") String key,
                        @PathParam(value = "code") String code,
                        @PathParam(value = "target") String target
    ) {
        // 客户端下线
        final DevCommand devCommand = devCommandFactory.getInstance(CommandType.OFFLINE.getId());
        final DataProtocol dataProtocol = DataProtocol.builder()
                .device(device)
                .key(key)
                .code(code)
                .target(target)
                .build();
        devCommand.execute(dataProtocol);
        // 从连接管理器移除
        final WebSocketCtx ctx = WebSocketCtx.builder()
                .code(code + key)
                .session(session)
                .build();
        webSocketCtxManager.removeCtx(ctx);
        // 输出日志
        log.info("client is offline. key is {}, code is {}, online count is {}",
                key,
                code,
                webSocketCtxManager.getOnlineCount());
    }

    /**
     * 错误事件。
     *
     * @param session   客户端连接。
     * @param throwable 错误信息。
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("websocket is error, cause is {}", throwable.getMessage());
    }

    /**
     * 自动装配命令处理接口工厂类。
     *
     * @param devCommandFactory 命令处理接口工厂类。
     */
    @Autowired
    public void setDevCommandFactory(DevCommandFactory devCommandFactory) {
        DevScreenEndpoint.devCommandFactory = devCommandFactory;
    }

    /**
     * 自动装配连接管理器。
     *
     * @param webSocketCtxManager 连接管理器。
     */
    @Autowired
    public void setWebSocketCtxManager(WebSocketCtxManager webSocketCtxManager) {
        DevScreenEndpoint.webSocketCtxManager = webSocketCtxManager;
    }

    /**
     * 命令处理接口工厂类。
     */
    private static DevCommandFactory devCommandFactory;

    /**
     * 连接管理器。
     */
    private static WebSocketCtxManager webSocketCtxManager;

}
