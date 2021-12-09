package cn.woyun.anov.gateway.dev.websocket.command;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.dev.websocket.enums.CommandType;
import cn.woyun.anov.gateway.dev.websocket.protocol.DataProtocol;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.entity.DevScreenLog;
import cn.woyun.anov.sdk.dev.service.DevScreenClientService;
import cn.woyun.anov.sdk.dev.service.DevScreenLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 日志命令业务处理类。
 *
 * @author xuepeng
 */
@Component
public class DevLogCommand extends DevAbstractCommand {

    /**
     * 执行命令。
     *
     * @param dataProtocol 数据协议。
     */
    @Override
    public void execute(final DataProtocol dataProtocol) {
        final DevScreenClient devScreenClient = devScreenClientService.findScreenByKeyAndCode(
                dataProtocol.getKey(), dataProtocol.getCode()
        );
        if (!Objects.isNull(devScreenClient)) {
            final DevScreenLog devScreenLog = BeanUtil.objToObj(devScreenClient, DevScreenLog.class);
            devScreenLog.setLogType(dataProtocol.getLogType());
            devScreenLog.setLogModule(dataProtocol.getLogModule());
            devScreenLog.setLogDesc(dataProtocol.getLogDesc());
            devScreenLogService.create(devScreenLog);
        }
    }

    /**
     * @return 日志命令。
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.LOG;
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
     * 自动装配大屏日志业务处理接口。
     *
     * @param devScreenLogService 大屏日志业务处理接口。
     */
    @Autowired
    public void setDevScreenLogService(DevScreenLogService devScreenLogService) {
        this.devScreenLogService = devScreenLogService;
    }

    /**
     * 大屏客户端的业务处理接口。
     */
    private DevScreenClientService devScreenClientService;

    /**
     * 大屏日志业务处理接口。
     */
    private DevScreenLogService devScreenLogService;

}
