package cn.woyun.anov.gateway.dev.websocket.command.strategy.online;

import cn.woyun.anov.gateway.dev.websocket.enums.DeviceType;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;

/**
 * 上线策略业务处理接口。
 *
 * @author xuepeng
 */
public interface OnlineStrategy {

    /**
     * 执行策略。
     *
     * @param dataProtocol 协议数据。
     */
    void handler(final DataProtocol dataProtocol);

    /**
     * @return 策略类型。
     */
    DeviceType getDeviceType();

}
