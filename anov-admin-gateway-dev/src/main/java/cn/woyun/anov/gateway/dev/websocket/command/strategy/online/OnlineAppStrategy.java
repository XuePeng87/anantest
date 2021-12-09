package cn.woyun.anov.gateway.dev.websocket.command.strategy.online;

import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtx;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import cn.woyun.anov.json.JsonUtil;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * APP上线策略。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class OnlineAppStrategy extends OnlineAbstractStrategy {

    /**
     * 执行策略。
     *
     * @param dataProtocol 协议数据。
     */
    @Override
    public void handler(DataProtocol dataProtocol) {
        try {
            if (isNotReady(dataProtocol)) return;
            final String code = dataProtocol.getCode();
            final String key = dataProtocol.getKey();
            final String target = dataProtocol.getTarget();
            final Session session = webSocketCtxManager.findByCode(code + key).getSession();
            // 控制端上线
            devScreenClientService.controllerOnline(key, code, target);
            // 准备通知大屏，控制设备已连接
            final WebSocketCtx ctx = webSocketCtxManager.findByCode(target + key);
            // 判断大屏是否连接
            if (Objects.isNull(ctx)) {
                session.getBasicRemote().sendText("连接失败,大屏未连接。");
                return;
            }
            // 将控制设备添加到大屏控制器集合中
            ctx.addController(code);
            // 查询大屏的全部控制终端信息
            final List<DevScreenClient> remoteClients = devScreenClientService.findRemoteClientByKeyAndCode(key, target);
            // 通知大屏，控制设备已连接
            final String message = "{\"type\":\"SYS\",\"confirm\":false,\"data\":\"connected\",\"apps\":"
                    + JsonUtil.convertObj2Str(remoteClients) + "}";
            ctx.getSession().getBasicRemote().sendText(message);
            session.getBasicRemote().sendText("连接成功。");
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

}
