package cn.woyun.anov.gateway.dev.websocket.command;

import cn.woyun.anov.gateway.dev.websocket.command.strategy.online.OnlineStrategy;
import cn.woyun.anov.gateway.dev.websocket.command.strategy.online.OnlineStrategyFactory;
import cn.woyun.anov.gateway.dev.websocket.enums.CommandType;
import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 上线命令业务处理类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class DevOnlineCommand extends DevAbstractCommand {

    /**
     * 执行命令。
     *
     * @param dataProtocol 数据协议。
     */
    @Override
    public void execute(final DataProtocol dataProtocol) {
        // 执行上线业务逻辑
        final OnlineStrategy onlineStrategy = StringUtils.equals(dataProtocol.getDevice(), DeviceType.PC.getId())
                ? onlineStrategyFactory.getInstance(DeviceType.PC)
                : onlineStrategyFactory.getInstance(DeviceType.APP);
        onlineStrategy.handler(dataProtocol);
    }

    /**
     * @return 上线命令。
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.ONLINE;
    }

    /**
     * 自动装配上线策略工厂类。
     *
     * @param onlineStrategyFactory 上线策略工厂类。
     */
    @Autowired
    public void setOnlineStrategyFactory(OnlineStrategyFactory onlineStrategyFactory) {
        this.onlineStrategyFactory = onlineStrategyFactory;
    }

    /**
     * 上线策略工厂类。
     */
    private OnlineStrategyFactory onlineStrategyFactory;

}
