package cn.woyun.anov.gateway.dev.websocket.command.strategy.online;

import cn.woyun.anov.gateway.dev.websocket.manager.WebSocketCtxManager;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import cn.woyun.anov.sdk.dev.entity.DevScreen;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import cn.woyun.anov.sdk.dev.service.DevScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;

/**
 * 上线策略的抽象类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public abstract class OnlineAbstractStrategy implements OnlineStrategy {

    /**
     * 检测是否具备上线的条件。
     *
     * @param dataProtocol 数据协议。
     * @return 是否可以上线。
     * @throws IOException 服务器IO异常。
     */
    protected boolean isNotReady(final DataProtocol dataProtocol) throws IOException {
        final String code = dataProtocol.getCode();
        final String key = dataProtocol.getKey();
        final Session session = webSocketCtxManager.findByCode(code + key).getSession();
        // 大屏是否存在
        final DevScreen devScreen = devScreenService.findByKey(key);
        if (Objects.isNull(devScreen)) {
            log.info("dev screen is not exist. key is {}", key);
            session.getBasicRemote().sendText("连接失败，大屏不存在");
            return true;
        }
        // 客户端是否存在
        final DevScreenClient client = devScreenClientService.findScreenByKeyAndCode(key, code);
        if (Objects.isNull(client)) {
            log.info("dev screen client is not exist. key is {}, code is {}", key, code);
            session.getBasicRemote().sendText("连接失败，客户端不存在");
            return true;
        }
        return false;
    }

    /**
     * 自动装配大屏的业务处理接口。
     *
     * @param devScreenService 大屏的业务处理接口。
     */
    @Autowired
    public void setDevScreenService(DevScreenService devScreenService) {
        this.devScreenService = devScreenService;
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
     * 大屏的业务处理接口。
     */
    private DevScreenService devScreenService;

    /**
     * WebSocket连接管理器。
     */
    protected WebSocketCtxManager webSocketCtxManager;

    /**
     * 大屏客户端的业务处理接口。
     */
    protected DevScreenClientService devScreenClientService;

}
