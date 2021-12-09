package cn.woyun.anov.gateway.dev.config.web;

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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

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
    public CorsFilter corsFilter(cn.woyun.anov.config.cors.CorsConfiguration corsConfiguration) {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Collections.singletonList(corsConfiguration.getOrigins()));
        corsConfig.addAllowedHeader(corsConfiguration.getHeaders());
        corsConfig.addAllowedMethod(corsConfiguration.getMethods());
        corsConfig.setAllowCredentials(corsConfiguration.isCredentials());
        corsConfig.setMaxAge(corsConfiguration.getAge());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsConfiguration.getMapping(), corsConfig);
        return new CorsFilter(source);
    }

    /**
     * 设置静态资源。
     *
     * @param registry 资源处理器。
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

    /**
     * @return WebSocket端点类。
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
