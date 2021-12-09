package cn.woyun.anov.config.verify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 验证码工具的配置信息。
 *
 * @author xuepeng
 */
@Data
@ConfigurationProperties(prefix = "anov.cpsp.verify-code")
public class VerifyCodeConfiguration {

    /**
     * 缓存的RedisKey前缀
     */
    private String redisKeyProfix = "ANOV:VERIFYCODE:";

    /**
     * 缓存过期时间
     */
    private Duration expiration = Duration.ofMinutes(2);

    /**
     * 图片宽度
     */
    private Integer captchaImgWidth = 111;

    /**
     * 图片高度
     */
    private Integer captchaImgHeight = 36;

    /**
     * 验证码长度。
     */
    private Integer captchaLength = 2;

    /**
     * 字体名称
     */
    private String fontName = "";

    /**
     * 字体大小
     */
    private Integer fontSize = 25;

}
