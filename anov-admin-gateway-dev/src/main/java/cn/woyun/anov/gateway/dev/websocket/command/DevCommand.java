package cn.woyun.anov.gateway.dev.websocket.command;

import cn.woyun.anov.gateway.dev.websocket.enums.CommandType;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;

/**
 * 大屏命令业务处理接口。
 *
 * @author xuepeng
 */
public interface DevCommand {

    /**
     * 执行命令。
     *
     * @param dataProtocol 数据协议。
     */
    void execute(final DataProtocol dataProtocol);

    /**
     * @return 获取命令类型。
     */
    CommandType getCommandType();

}
