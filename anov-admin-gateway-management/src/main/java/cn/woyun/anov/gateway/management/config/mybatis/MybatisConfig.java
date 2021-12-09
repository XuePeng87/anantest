package cn.woyun.anov.gateway.management.config.mybatis;

import cn.woyun.anov.config.mgt.SysTenantConfiguration;
import cn.woyun.anov.gateway.management.config.security.WebSecurityAuthenticationDetails;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * Mybatis配置信息。
 *
 * @author xuepeng
 */
@Configuration
@MapperScan(basePackages = {
        "cn.woyun.anov.sdk.mgt.mapper",
        "cn.woyun.anov.sdk.dev.mapper"
})
public class MybatisConfig {

    /**
     * 超级管理员。
     */
    private static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    /**
     * 系统管理员。
     */
    private static final String SYSTEM_ADMIN = "ROLE_SYSTEM_ADMIN";

    /**
     * @return 设置分页拦截器。
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        //你的最大单页限制数量，默认 100 条，小于 0 如 -1 不受限制
        paginationInterceptor.setMaxLimit(100L);
        return paginationInterceptor;
    }

    /**
     * @return 设置租户拦截器。
     */
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(SysTenantConfiguration sysTenantConfiguration) {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            /**
             * @return 获取租户主键。
             */
            @Override
            public Expression getTenantId() {
                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (Objects.isNull(authentication)) {
                    return null;
                }
                final Long tenantId = ((WebSecurityAuthenticationDetails) authentication.getDetails()).getTenantId();
                if (Objects.isNull(tenantId)) {
                    return null;
                }
                return new StringValue(String.valueOf(tenantId));
            }

            /**
             * @return 获取租户字段名称。
             */
            @Override
            public String getTenantIdColumn() {
                return sysTenantConfiguration.getTenantIdColumn();
            }

            /**
             * 设置不过滤租户的数据表。
             *
             * @param tableName 表名。
             * @return 是否过滤。
             */
            @Override
            public boolean ignoreTable(String tableName) {
                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!Objects.isNull(authentication) && !StringUtils.equals(authentication.getName(), "anonymousUser")) {
                    final WebSecurityAuthenticationDetails webSecurityAuthenticationDetails
                            = (WebSecurityAuthenticationDetails) authentication.getDetails();
                    if (webSecurityAuthenticationDetails.getRoles().contains(SUPER_ADMIN)
                            || webSecurityAuthenticationDetails.getRoles().contains(SYSTEM_ADMIN)) {
                        return true;
                    }
                }
                return sysTenantConfiguration.getIgnoresTables().contains(tableName);
            }
        });
    }

    /**
     * @return 设置分页插件。
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(SysTenantConfiguration sysTenantConfiguration) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor(sysTenantConfiguration));
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        return interceptor;
    }

}
