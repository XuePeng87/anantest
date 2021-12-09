package cn.woyun.anov.config;

import cn.woyun.anov.config.cors.CorsConfiguration;
import cn.woyun.anov.config.dev.DevScreenConfiguration;
import cn.woyun.anov.config.ipcity.IpCityConfiguration;
import cn.woyun.anov.config.mgt.SysTenantConfiguration;
import cn.woyun.anov.config.mgt.SysUserConfiguration;
import cn.woyun.anov.config.oss.OssConfiguration;
import cn.woyun.anov.config.verify.VerifyCodeConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Anov管理平台配置文件管理。
 */
@Configuration
@EnableConfigurationProperties(value = {
        CorsConfiguration.class,
        SysUserConfiguration.class,
        SysTenantConfiguration.class,
        DevScreenConfiguration.class,
        VerifyCodeConfiguration.class,
        OssConfiguration.class,
        IpCityConfiguration.class
})
public class AnovAdminAutoConfiguation {

    /**
     * @return 跨域配置。
     */
    @Bean
    public CorsConfiguration corsConfiguration() {
        return new CorsConfiguration();
    }

    /**
     * @return 用户管理配置。
     */
    @Bean
    public SysUserConfiguration sysUserConfiguration() {
        return new SysUserConfiguration();
    }

    /**
     * @return 租户管理配置。
     */
    @Bean
    public SysTenantConfiguration sysTenantConfiguration() {
        return new SysTenantConfiguration();
    }

    /**
     * @return 大屏管理配置。
     */
    @Bean
    public DevScreenConfiguration devScreenConfiguration() {
        return new DevScreenConfiguration();
    }

    /**
     * @return 验证码工具配置。
     */
    @Bean
    public VerifyCodeConfiguration verifyCodeConfiguration() {
        return new VerifyCodeConfiguration();
    }

}
