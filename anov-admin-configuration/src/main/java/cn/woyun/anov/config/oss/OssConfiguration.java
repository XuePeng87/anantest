package cn.woyun.anov.config.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OSS工具的配置信息。
 *
 * @author xuepeng
 */
@Data
@ConfigurationProperties(prefix = "anov.cpsp.oss")
public class OssConfiguration {

    /**
     * 地址。
     */
    private String endpoint;

    /**
     * 访问令牌。
     */
    private String ak;

    /**
     * 密钥令牌。
     */
    private String sk;

    /**
     * 是否开启安全模式。
     */
    private Boolean securt;

}
