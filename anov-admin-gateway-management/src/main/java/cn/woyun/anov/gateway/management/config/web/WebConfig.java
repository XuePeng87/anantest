package cn.woyun.anov.gateway.management.config.web;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 日期格式化表达式。
     */
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    /**
     * 设置跨域过滤器。
     *
     * @return 跨域过滤器。
     */
    @Bean
    @Profile({"dev", "test"})
    public CorsConfigurationSource corsConfigurationSource(cn.woyun.anov.config.cors.CorsConfiguration corsConfiguration) {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList(corsConfiguration.getOrigins()));
        configuration.setAllowedMethods(Collections.singletonList(corsConfiguration.getMethods()));
        configuration.setAllowedHeaders(Collections.singletonList(corsConfiguration.getHeaders()));
        configuration.setAllowCredentials(corsConfiguration.isCredentials());
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsConfiguration.getMapping(), configuration);
        return source;
    }

    /**
     * 允许Knife4j文档方案。
     *
     * @param registry 静态资源处理器。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 创建一个ClientHttpRequestFactory工厂对象。
     *
     * @return ClientHttpRequestFactory工厂对象。
     */
    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        return factory;
    }

    /**
     * @return 创建LocalDateTime的序列化对象。
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @return 创建LocalDateTime的反序列化对象。
     */
    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @return 创建LocalDateTime的反序列化对象。
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
        };
    }

    /**
     * 创建一个RestTemplate对象。
     *
     * @param factory ClientHttpRequestFactory工厂对象。
     * @return RestTemplate对象。
     */
    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

}
