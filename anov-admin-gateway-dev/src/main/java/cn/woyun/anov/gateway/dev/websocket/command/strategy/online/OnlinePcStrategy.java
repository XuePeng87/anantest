package cn.woyun.anov.gateway.dev.websocket.command.strategy.online;

import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;

/**
 * PC上线策略。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class OnlinePcStrategy extends OnlineAbstractStrategy {

    /**
     * 执行策略。
     *
     * @param dataProtocol 协议数据。
     */
    @Override
    public void handler(final DataProtocol dataProtocol) {
        try {
            if (isNotReady(dataProtocol)) return;
            final String code = dataProtocol.getCode();
            final String key = dataProtocol.getKey();
            final Session session = webSocketCtxManager.findByCode(code + key).getSession();
            devScreenClientService.screenOnline(key, code);
            session.getBasicRemote().sendText("连接成功。");
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

}
