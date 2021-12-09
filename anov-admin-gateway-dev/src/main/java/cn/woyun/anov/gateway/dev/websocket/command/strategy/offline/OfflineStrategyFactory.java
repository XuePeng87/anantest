package cn.woyun.anov.gateway.dev.websocket.command.strategy.offline;

import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 下线策略工厂类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class OfflineStrategyFactory {

    /**
     * 根据设备类型获得一个下线策略。
     *
     * @param deviceType 设备类型。
     * @return 下线策略。
     */
    public OfflineStrategy getInstance(final DeviceType deviceType) {
        if (log.isDebugEnabled()) {
            log.debug("get {} strategy from factory.", deviceType.getId());
        }
        Optional<OfflineStrategy> strategy = offlineStrategies.stream()
                .filter(s -> s.getDeviceType().equals(deviceType))
                .findFirst();
        if (strategy.isPresent()) {
            return strategy.get();
        } else {
            log.error("get strategy by mode failed, mode is {}", deviceType.getId());
            throw new IllegalArgumentException("从工厂中获取策略失败");
        }
    }

    /**
     * 自动装配下线策略接口集合。
     *
     * @param offlineStrategies 下线策略接口集合。
     */
    @Autowired
    public void setOfflineStrategies(List<OfflineStrategy> offlineStrategies) {
        this.offlineStrategies = offlineStrategies;
    }

    /**
     * 下线策略接口集合。
     */
    private List<OfflineStrategy> offlineStrategies;

}
