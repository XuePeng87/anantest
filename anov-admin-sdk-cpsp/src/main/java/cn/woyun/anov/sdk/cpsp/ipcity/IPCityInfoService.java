package cn.woyun.anov.sdk.cpsp.ipcity;

/**
 * IP归属地业务处理接口。
 *
 * @author xuepeng
 */
public interface IPCityInfoService {

    /**
     * @return 服务的模式。
     */
    IPCityInfoMode getMode();

    /**
     * 查询IP归属地。
     *
     * @param ip 请求IP。
     * @return 归属地。
     */
    String findIPCityInfo(final String ip);

}
