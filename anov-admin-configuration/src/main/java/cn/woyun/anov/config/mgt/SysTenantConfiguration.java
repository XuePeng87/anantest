package cn.woyun.anov.config.mgt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * 租户管理的配置信息。
 *
 * @author xuepeng
 */
@Data
@ConfigurationProperties(prefix = "anov.mgt.tenant")
public class SysTenantConfiguration {

    /**
     * 租户的过期时间。
     */
    private Duration expiration = Duration.ofDays(30);

    /**
     * 租户的创建者。
     */
    private String createUser = "superadmin";

    /**
     * 不进行租户过滤的数据表。
     */
    private Set<String> ignoresTables = new HashSet<>();

    /**
     * 租户字段名称。
     */
    private String tenantIdColumn = "tenant_id";

}
