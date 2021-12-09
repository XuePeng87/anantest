package cn.woyun.anov.gateway.dev.websocket.command;

import cn.woyun.anov.gateway.dev.websocket.command.strategy.offline.OfflineStrategy;
import cn.woyun.anov.gateway.dev.websocket.command.strategy.offline.OfflineStrategyFactory;
import cn.woyun.anov.gateway.dev.websocket.enums.CommandType;
import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 下线命令业务处理类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class DevOfflineCommand extends DevAbstractCommand {

    /**
     * 执行命令。
     *
     * @param dataProtocol 数据协议。
     */
    @Override
    public void execute(final DataProtocol dataProtocol) {
        final OfflineStrategy offlineStrategy = StringUtils.equals(dataProtocol.getDevice(), DeviceType.PC.getId())
                ? offlineStrategyFactory.getInstance(DeviceType.PC)
                : offlineStrategyFactory.getInstance(DeviceType.APP);
        offlineStrategy.handler(dataProtocol);
    }

    /**
     * @return 下线命令。
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.OFFLINE;
    }

    /**
     * 自动装配下线策略工厂类。
     *
     * @param offlineStrategyFactory 下线策略工厂类。
     */
    @Autowired
    public void setOfflineStrategyFactory(OfflineStrategyFactory offlineStrategyFactory) {
        this.offlineStrategyFactory = offlineStrategyFactory;
    }

    /**
     * 下线策略工厂类。
     */
    private OfflineStrategyFactory offlineStrategyFactory;

}
