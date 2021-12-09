package cn.woyun.anov.gateway.dev.websocket.command.strategy.offline;

import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;

/**
 * 下线策略业务处理接口。
 *
 * @author xuepeng
 */
public interface OfflineStrategy {

    /**
     *
     * @param dataProtocol 协议数据。
     */
    void handler(final DataProtocol dataProtocol);

    /**
     * @return 策略类型。
     */
    DeviceType getDeviceType();

}
