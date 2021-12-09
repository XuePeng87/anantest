package cn.woyun.anov.gateway.dev.websocket.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的Session对象。
 *
 * @author xuepeng
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WebSocketCtx {

    /**
     * 客户端唯一标识。
     */
    private String code;

    /**
     * 控制器集合。
     */
    private Set<String> controllers = new HashSet<>();

    /**
     * 连接会话。
     */
    private Session session;

    /**
     * 添加控制器。
     *
     * @param controller 控制器唯一标识。
     */
    public void addController(final String controller) {
        controllers.add(controller);
    }

    /**
     * 移除控制器。
     *
     * @param controller 控制器唯一标识。
     */
    public void removeController(final String controller) {
        controllers.remove(controller);
    }

    /**
     * 获取控制器的数量。
     */
    public int getControllerCount() {
        return controllers.size();
    }

}
