package cn.woyun.anov.config.ipcity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * IP归属工具的配置信息。
 *
 * @author xuepeng
 */
@Data
@ConfigurationProperties(prefix = "anov.cpsp.ipcity")
public class IpCityConfiguration {

    /**
     * 查询IP归属的API接口地址
     */
    private String apiUrl;

    /**
     * 是否启用API接口
     */
    private Boolean internet;

}
