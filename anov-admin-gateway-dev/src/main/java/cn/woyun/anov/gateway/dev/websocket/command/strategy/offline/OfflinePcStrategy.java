package cn.woyun.anov.gateway.dev.websocket.command.strategy.offline;

import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtx;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtxManager;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * PC下线策略。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class OfflinePcStrategy implements OfflineStrategy {

    /**
     * 执行策略。
     *
     * @param dataProtocol 协议数据。
     */
    @Override
    public void handler(final DataProtocol dataProtocol) {
        try {
            final String key = dataProtocol.getKey();
            final String code = dataProtocol.getCode();
            // 大屏下线，所有控制器也下线
            final WebSocketCtx ctx = webSocketCtxManager.findByCode(code + key);
            devScreenClientService.screenOffline(key, code);
            // 通知所有控制器下线
            for (final String controller : ctx.getControllers()) {
                final WebSocketCtx controllerCtx = webSocketCtxManager.findByCode(controller + key);
                if (!Objects.isNull(controllerCtx)) {
                    final String message = "{\"type\":\"SYS\",\"confirm\":false,\"data\":\"quit\"}";
                    controllerCtx.getSession().getBasicRemote().sendText(message);
                }
            }
        } catch (IOException e) {
            log.error("server error, cause is {}", e.getMessage());
        }
    }

    /**
     * @return PC类型策略。
     */
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.PC;
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
