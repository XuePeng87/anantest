package cn.woyun.anov.config.dev;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 大屏管理的配置信息。
 *
 * @author xuepeng
 */
@Data
@ConfigurationProperties(prefix = "anov.dev.screen")
public class DevScreenConfiguration {

    /**
     * 大屏的过期时间。
     */
    private Duration expiration = Duration.ofDays(365);

    /**
     * 请求时间区间。
     */
    private Duration requestTimeRange = Duration.ofSeconds(30);

}
