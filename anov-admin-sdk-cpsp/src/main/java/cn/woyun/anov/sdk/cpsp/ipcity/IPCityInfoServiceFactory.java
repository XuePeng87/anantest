package cn.woyun.anov.sdk.cpsp.ipcity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * IP归属查询服务工厂类。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class IPCityInfoServiceFactory {

    /**
     * 根据网络模式获取一个服务接口。
     *
     * @param ipCityInfoMode 网络模式。
     * @return 服务接口。
     */
    public IPCityInfoService getInstance(final IPCityInfoMode ipCityInfoMode) {
        if (log.isDebugEnabled()) {
            log.debug("get {} service from factory.", ipCityInfoMode);
        }
        Optional<IPCityInfoService> ipCityInfoService = ipCityInfoServices.stream()
                .filter(s -> s.getMode().equals(ipCityInfoMode)).findFirst();
        if (ipCityInfoService.isPresent()) {
            return ipCityInfoService.get();
        } else {
            log.error("get service by mode failed, mode is {}", ipCityInfoMode);
            throw new IllegalArgumentException("从工厂中获取服务失败");
        }
    }

    /**
     * 自动装配IP归属查询业务处理接口集合。
     *
     * @param ipCityInfoServices IP归属查询业务处理接口集合。
     */
    @Autowired
    public void setIpCityInfoServices(List<IPCityInfoService> ipCityInfoServices) {
        this.ipCityInfoServices = ipCityInfoServices;
    }

    /**
     * IP归属查询业务处理接口集合。
     */
    private List<IPCityInfoService> ipCityInfoServices;

}
