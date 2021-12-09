package cn.woyun.anov.gateway.management.filter;

import cn.woyun.anov.xss.XSSEscapeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * 过滤器的配置对象。
 *
 * @author xuepeng
 */
@Configuration
public class FilterConfig {

    /**
     * @return 注册XSS过滤器。
     */
    @Bean
    public FilterRegistrationBean<Filter> registLogFilter() {
        // 注册过滤器。
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        // 使用框架内置的规则。
        registration.setFilter(new XSSEscapeFilter());
        registration.addUrlPatterns("/*");
        registration.setName("XSSFilter");
        registration.setOrder(1);
        return registration;
    }

}
