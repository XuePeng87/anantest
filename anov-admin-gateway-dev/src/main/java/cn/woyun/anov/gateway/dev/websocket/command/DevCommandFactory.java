package cn.woyun.anov.gateway.dev.websocket.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 大屏命令业务处理接口工厂类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class DevCommandFactory {

    /**
     * 获取大屏命令业务处理接口。
     *
     * @param commandType 命令类型。
     * @return 大屏命令业务处理接口。
     */
    public DevCommand getInstance(final String commandType) {
        final Optional<DevCommand> instance = commands
                .stream()
                .filter(s -> s.getCommandType().getId().equals(commandType))
                .findFirst();
        if (instance.isPresent()) {
            return instance.get();
        } else {
            log.error("get service by mode failed, type is {}", commandType);
            throw new IllegalArgumentException("请检查传入的命令类型");
        }
    }

    /**
     * 自动装配命令业务处理接口集合。
     *
     * @param commands 命令业务处理接口集合。
     */
    @Autowired
    public void setCommands(List<DevCommand> commands) {
        this.commands = commands;
    }

    /**
     * 命令业务处理接口集合。
     */
    private List<DevCommand> commands;

}
