package cn.woyun.anov.sdk.cpsp.ipcity;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 查询IP归属地的本地模式实现类。
 *
 * @author xuepeng
 */
@Slf4j
@Service
public class IPCityInfoLocalServiceImpl implements IPCityInfoService {

    /**
     * 网络归属地文件。
     */
    private static File file;

    /**
     * 网络归属地数据库。
     */
    private static DbConfig config;

    static {
        final String path = "ip2region/ip2region.db";
        try {
            config = new DbConfig();
        } catch (DbMakerConfigException e) {
            log.error("ip2region.db init failed. cause is {}", e.getMessage());
        }
        try (InputStream in = new ClassPathResource(path).getInputStream()) {
            file = new File(path);
            FileUtils.copyInputStreamToFile(in, file);
        } catch (IOException e) {
            log.error("ip2regin.db init failed. cause is {}", e.getMessage());
        }
    }

    /**
     * 本地查询
     */
    @Override
    public IPCityInfoMode getMode() {
        return IPCityInfoMode.LOCAL;
    }

    /**
     * 查询IP归属地。
     *
     * @param ip 请求IP。
     * @return 归属地。
     */
    @Override
    public String findIPCityInfo(final String ip) {
        String address = StringUtils.EMPTY;
        try {
            final DataBlock dataBlock = new DbSearcher(config, file.getPath()).binarySearch(ip);
            final String region = dataBlock.getRegion();
            address = region.replace("0|", "");
            if (address.charAt(address.length() - 1) == '|') {
                address = address.substring(0, address.length() - 1);
            }
            return address;
        } catch (IOException e) {
            log.error("find ip city info from local database failed. cause is {}", e.getMessage());
        }
        return address;
    }

}
