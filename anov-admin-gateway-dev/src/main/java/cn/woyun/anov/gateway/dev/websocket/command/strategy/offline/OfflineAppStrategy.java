package cn.woyun.anov.gateway.dev.websocket.command.strategy.offline;

import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtx;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtxManager;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import cn.woyun.anov.json.JsonUtil;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * APP下线策略。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class OfflineAppStrategy implements OfflineStrategy {

    /**
     * 执行策略。
     *
     * @param dataProtocol 协议数据。
     */
    @Override
    public void handler(DataProtocol dataProtocol) {
        try {
            final String key = dataProtocol.getKey();
            final String code = dataProtocol.getCode();
            final String target = dataProtocol.getTarget();
            // 通知大屏控制器下线
            devScreenClientService.controllerOffline(key, code);
            final WebSocketCtx ctx = webSocketCtxManager.findByCode(target + key);
            if (!Objects.isNull(ctx)) {
                ctx.getControllers().remove(code);
                // 查询大屏的全部控制终端信息
                final List<DevScreenClient> remoteClients = devScreenClientService.findRemoteClientByKeyAndCode(key, target);
                final String message = "{\"type\":\"SYS\",\"confirm\":false,\"data\":\"disconnect\",\"apps\":"
                        + JsonUtil.convertObj2Str(remoteClients) + "}";
                ctx.getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            log.error("server error, cause is {}", e.getMessage());
        }
    }

    /**
     * @return APP类型策略。
     */
    @Override
    public DeviceType getDeviceType() {
        return DeviceType.APP;
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
     * 自动装配大屏客户端的业务处理接口。
     *
     * @param devScreenClientService 大屏客户端的业务处理接口。
     */
    @Autowired
    public void setDevScreenClientService(DevScreenClientService devScreenClientService) {
        this.devScreenClientService = devScreenClientService;
    }

    /**
     * WebSocket连接管理器。
     */
    private WebSocketCtxManager webSocketCtxManager;

    /**
     * 大屏客户端的业务处理接口。
     */
    private DevScreenClientService devScreenClientService;

}
