package cn.woyun.anov.gateway.dev.websocket.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocketSession管理器的实现类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class WebSocketCtxManagerImpl implements WebSocketCtxManager {

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * 客户端与Session的关系表。
     */
    private static final Map<String, WebSocketCtx> CTX_MAP = new ConcurrentHashMap<>();

    /**
     * 将连接添加到管理器中。
     *
     * @param ctx WebSocketSession对象。
     */
    @Override
    public void addCtx(final WebSocketCtx ctx) {
        // 将连接添加到管理器
        if (CTX_MAP.containsKey(ctx.getCode())) {
            log.warn("ctx {} is existed in manager, override current ctx.", ctx.getCode());
        }
        CTX_MAP.put(ctx.getCode(), ctx);
        // 上线数量+1
        ONLINE_COUNT.incrementAndGet();
    }

    /**
     * 将连接从管理中移除。
     *
     * @param ctx WebSocketSession对象。
     */
    @Override
    public void removeCtx(final WebSocketCtx ctx) {
        // 将连接从管理器中移除。
        if (!CTX_MAP.containsKey(ctx.getCode())) {
            log.warn("client {} is not in manager, can not remove.", ctx.getCode());
        } else {
            CTX_MAP.remove(ctx.getCode());
            ONLINE_COUNT.decrementAndGet();
        }
    }

    /**
     * 从管理器中获取连接。
     *
     * @param code 客户端唯一标识。
     * @return 连接。
     */
    @Override
    public WebSocketCtx findByCode(final String code) {
        final WebSocketCtx ctx = CTX_MAP.get(code);
        if (Objects.isNull(ctx)) {
            log.info("client {} not in manager.", code);
        }
        return ctx;
    }

    /**
     * @return 获取当前在线数。
     */
    @Override
    public int getOnlineCount() {
        return ONLINE_COUNT.get();
    }

}
