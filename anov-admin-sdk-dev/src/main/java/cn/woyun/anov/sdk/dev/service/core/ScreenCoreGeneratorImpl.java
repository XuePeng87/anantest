package cn.woyun.anov.sdk.dev.service.core;

import cn.woyun.anov.sdk.dev.entity.DevScreen;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * 大屏芯片生成器实现类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class ScreenCoreGeneratorImpl implements ScreenCoreGenerator {

    /**
     * 生成大屏芯片。
     *
     * @param devScreen 大屏信息。
     * @return 芯片。
     */
    @Override
    public String generate(final DevScreen devScreen) {
        final String param = CoreParam.createCoreParam(devScreen);
        return this.invokeNodeJsApi(param);
    }

    /**
     * 调用NodeJsAPI生成芯片。
     *
     * @param param 生成参数。
     * @return 芯片的Base64字符串。
     */
    private String invokeNodeJsApi(final String param) {
        try {
            final HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            final HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
            ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:3000/createPng?param={param}",
                    HttpMethod.GET,
                    requestEntity,
                    String.class,
                    param);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            log.error("generate dev core failed, call nodejs api http status code is {}", e.getStatusCode());
            return StringUtils.EMPTY;
        } catch (Exception e) {
            log.error("generate dev core failed, cause is {}", e.getMessage());
            return StringUtils.EMPTY;
        }
    }

    /**
     * 自动装配HTTP请求对象。
     *
     * @param restTemplate HTTP请求对象。
     */
    @Autowired(required = false)
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * HTTP请求对象。
     */
    private RestTemplate restTemplate;

}
