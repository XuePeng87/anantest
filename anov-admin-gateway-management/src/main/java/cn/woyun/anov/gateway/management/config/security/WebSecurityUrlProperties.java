package cn.woyun.anov.gateway.management.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 定义SpringSecurity所需的Url。
 *
 * @author xuepeng
 */
@Data
@Component
@ConfigurationProperties("anov.security")
public class WebSecurityUrlProperties {

    /**
     * 登录Url。
     */
    private String signin;
    /**
     * 登出Url。
     */
    private String signout;
    /**
     * 忽略的Url（不鉴权）。
     */
    private Set<String> ignoreUrls;
    /**
     * 登录页面地址。
     */
    private String loginPage;

}
