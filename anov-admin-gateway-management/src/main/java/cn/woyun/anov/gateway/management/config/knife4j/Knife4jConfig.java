package cn.woyun.anov.gateway.management.config.knife4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j的配置类。
 *
 * @author xuepeng
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {

    /**
     * @return 创建API文档。
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.woyun.anov.gateway.management.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @return 创建API信息。
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("Anov管理系统API文档")
                .contact(new Contact("薛鹏", "https://github.com.lenve", "100103081@qq.com"))
                .version("v1.0.0")
                .title("Anov管理系统API文档")
                .build();
    }

}
