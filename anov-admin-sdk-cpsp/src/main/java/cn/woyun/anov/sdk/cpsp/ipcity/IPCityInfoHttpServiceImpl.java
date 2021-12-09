package cn.woyun.anov.sdk.cpsp.ipcity;

import cn.woyun.anov.config.ipcity.IpCityConfiguration;
import cn.woyun.anov.json.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * 查询IP归属地的网络请求模式实现类。
 *
 * @author xuepeng
 */
@Slf4j
@Service
public class IPCityInfoHttpServiceImpl implements IPCityInfoService {

    /**
     * 本地查询
     */
    @Override
    public IPCityInfoMode getMode() {
        return IPCityInfoMode.HTTP;
    }

    /**
     * 查询IP归属地。
     *
     * @param ip 请求IP。
     * @return 归属地。
     */
    @Override
    public String findIPCityInfo(final String ip) {
        String address;
        try {
            final ResponseEntity<String> response = restTemplate.getForEntity(
                    ipCityConfiguration.getApiUrl(), String.class, ip
            );
            final String body = response.getBody();
            final IPCityInfo ipCityInfo = JsonUtil.convertStr2Obj(body, IPCityInfo.class);
            if (!StringUtils.isBlank(ipCityInfo.err)) {
                log.error("find ip city info failed, ip is {}, cause is {}", ip, ipCityInfo.err);
            }
            address = ipCityInfo.getCity();
        } catch (HttpStatusCodeException e) {
            log.error("find ip city info from api failed，invoke [whois.pconline.com.cn] http status code is {}", e.getStatusCode());
            return StringUtils.EMPTY;
        }
        return address;
    }

    /**
     * 自动装配HTTP Client对象。
     *
     * @param restTemplate HTTP Client对象。
     */
    @Autowired(required = false)
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * HTTP Client对象。
     */
    private RestTemplate restTemplate;

    /**
     * IP归属地的响应结果。
     *
     * @author xuepeng
     */
    @Data
    public static class IPCityInfo {

        /**
         * 错误码
         */
        private String err;

        /**
         * 归属地
         */
        private String addr;

        /**
         * 城市
         */
        private String city;

    }

    /**
     * 自动装配IP归属工具的配置信息。
     *
     * @param ipCityConfiguration IP归属工具的配置信息。
     */
    @Autowired
    public void setIpCityConfiguration(IpCityConfiguration ipCityConfiguration) {
        this.ipCityConfiguration = ipCityConfiguration;
    }

    /**
     * IP归属工具的配置信息。
     */
    private IpCityConfiguration ipCityConfiguration;

}
