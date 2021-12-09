package cn.woyun.anov.gateway.management.config.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Session的配置类。
 * maxInactiveIntervalInSeconds用来设置超时时间，单位是秒数。
 *
 * @author xuepeng
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class SessionConfig {

    /**
     * @return 自定义Cookie的samesite属性，解决SpringBoot2.1.5以上版本，跨域请求时SessionID变化的问题。
     */
    @Bean
    @Profile({"dev", "test",})
    public CookieSerializer httpSessionIdResolverForDev() {
        final DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 跨域允许传输cookie
        cookieSerializer.setSameSite("null");
        return cookieSerializer;
    }

    /**
     * @return 自定义Cookie的samesite属性，在docker环境下是同源的
     */
    @Bean
    @Profile({"docker", "prod"})
    public CookieSerializer httpSessionIdResolverForSameSite() {
        final DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 仅限同一站点允许传输cookie
        cookieSerializer.setSameSite("Trust");
        return cookieSerializer;
    }

}
