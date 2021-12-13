package cn.woyun.anov.gateway.management.controller.mgt;

import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志debug控制器。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/logger")
@Slf4j
public class LoggerController {

    /**
     * @return 打开日志debug模式。
     */
    @PutMapping("/v1/open")
    public HttpResult<String> openDebug() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        ctx.getConfiguration().getLoggerConfig("ROOT").setLevel(Level.DEBUG);
        ctx.updateLoggers();
        log.info("日期开启debug模式。");
        return DefaultHttpResultFactory.success("日志开启debug模式。", StringUtils.EMPTY);
    }

    /**
     * @return 关闭日志debug模式。
     */
    @PutMapping("/v1/close")
    public HttpResult<String> closeDebug() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        ctx.getConfiguration().getLoggerConfig("ROOT").setLevel(Level.INFO);
        ctx.updateLoggers();
        log.info("日期关闭debug模式。");
        return DefaultHttpResultFactory.success("日志关闭debug模式。", StringUtils.EMPTY);
    }

}
