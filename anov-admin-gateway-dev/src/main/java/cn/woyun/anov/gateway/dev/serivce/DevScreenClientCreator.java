package cn.woyun.anov.gateway.dev.serivce;

import cn.woyun.anov.config.ipcity.IpCityConfiguration;
import cn.woyun.anov.gateway.dev.consts.DevScreenConsts;
import cn.woyun.anov.sdk.cpsp.ipcity.IPCityInfoMode;
import cn.woyun.anov.sdk.cpsp.ipcity.IPCityInfoServiceFactory;
import cn.woyun.anov.sdk.dev.entity.DevScreenAnalytics;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.web.WebUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 大屏客户端创建器。
 *
 * @author xuepeng
 */
@Component
public class DevScreenClientCreator {

    /**
     * 创建一个大屏客户端实体类。
     *
     * @param request 请求对象。
     * @return 大屏客户端。
     */
    public DevScreenClient create(final HttpServletRequest request) {
        final DevScreenClient devScreenClient = new DevScreenClient();
        // 获取GA信息
        final DevScreenAnalytics devScreenAnalytics = new DevScreenAnalytics(request.getHeader(DevScreenConsts.GA));
        // 获取UserAgent信息
        final UserAgent agents = UserAgent.parseUserAgentString(request.getHeader(DevScreenConsts.USER_AGENT));
        // 获取IP和IP归属信息
        final String requestIp = WebUtil.getIpAddress(request);
        final IPCityInfoMode mode = BooleanUtils.isTrue(ipCityConfiguration.getInternet()) ? IPCityInfoMode.HTTP : IPCityInfoMode.LOCAL;
        final String address = ipCityInfoServiceFactory.getInstance(mode).findIPCityInfo(requestIp);
        // 大屏信息
        devScreenClient.setScreenLevel(request.getHeader(DevScreenConsts.CLIENT_LEVEL));
        devScreenClient.setScreenEnv(request.getHeader(DevScreenConsts.CLIENT_IS_DEBUG));
        devScreenClient.setScreenDir(request.getHeader(DevScreenConsts.CLIENT_DIR));
        devScreenClient.setScreenFrameworkVersion(request.getHeader(DevScreenConsts.CLIENT_VERSION));
        devScreenClient.setScreenCoreVersion(request.getHeader(DevScreenConsts.CLIENT_CORE_VERSION));
        devScreenClient.setScreenHasChildren(Boolean.FALSE);
        devScreenClient.setScreenLeafed(Boolean.FALSE);
        // 客户端信息
        devScreenClient.setClientRemoteCode(StringUtils.EMPTY);
        devScreenClient.setClientOnlined(Boolean.FALSE);
        devScreenClient.setClientType(request.getHeader(DevScreenConsts.CLIENT_TYPE));
        devScreenClient.setClientTitle(devScreenAnalytics.getTitle());
        devScreenClient.setClientLanguage(devScreenAnalytics.getLanguage());
        devScreenClient.setClientResoliving(devScreenAnalytics.getWidth() + "/" + devScreenAnalytics.getHeight());
        // 网络信息
        devScreenClient.setClientRequestIp(requestIp);
        devScreenClient.setClientRequestIpInfo(address);
        devScreenClient.setClientRequestUrl(devScreenAnalytics.getUrl());
        devScreenClient.setClientDomain(devScreenAnalytics.getDomain());
        devScreenClient.setClientReferer(devScreenAnalytics.getReferer());
        // 浏览器信息
        devScreenClient.setClientBrowserName(agents.getBrowser().getName());
        devScreenClient.setClientBrowserVersion(
                Objects.isNull(agents.getBrowserVersion()) ? StringUtils.EMPTY : agents.getBrowserVersion().getVersion()
        );
        devScreenClient.setClientAgent(agents.toString());
        // 操作系统信息
        devScreenClient.setClientOsName(agents.getOperatingSystem().getName());
        devScreenClient.setClientOsInfo(agents.getOperatingSystem().getDeviceType().getName());
        devScreenClient.setClientOsFamily(agents.getOperatingSystem().getGroup().getName());
        // 显卡信息
        devScreenClient.setClientGpuName(devScreenAnalytics.getGpuName());
        devScreenClient.setClientGpuFamily(devScreenAnalytics.getGpuFamily());
        devScreenClient.setClientColour(devScreenAnalytics.getColor());
        devScreenClient.setClientFps(devScreenAnalytics.getFps());
        // 内存信息
        devScreenClient.setClientMemTotal(devScreenAnalytics.getMemTotal());
        devScreenClient.setClientMemUsed(devScreenAnalytics.getMemUsed());
        return devScreenClient;
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
     * 自动装配IP归属地查询服务工厂。
     *
     * @param ipCityInfoServiceFactory IP归属地查询服务工厂。
     */
    @Autowired
    public void setIpCityInfoServiceFactory(IPCityInfoServiceFactory ipCityInfoServiceFactory) {
        this.ipCityInfoServiceFactory = ipCityInfoServiceFactory;
    }

    /**
     * IP归属工具的配置信息。
     */
    private IpCityConfiguration ipCityConfiguration;

    /**
     * IP归属地查询服务工厂。
     */
    private IPCityInfoServiceFactory ipCityInfoServiceFactory;

}
