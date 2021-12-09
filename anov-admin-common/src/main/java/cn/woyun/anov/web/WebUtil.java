package cn.woyun.anov.web;

import cn.woyun.anov.consts.PunctuationConst;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP地址的工具类。
 */
public class WebUtil {

    /**
     * IP所在的Header位置。
     */
    private static final String X_FORWARDED_FOR = "x-forwarded-for";
    /**
     * 不识别。
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 本机IP。
     */
    private static final String LOCALHOST = "localhost";
    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    /**
     * 构造函数。
     */
    private WebUtil() {
    }

    /**
     * 获取真实IP。
     *
     * @param request HttpServletRequest。
     * @return 真实IP。
     */
    public static String getRealIpAddress(final HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (StringUtils.isNotEmpty(ip)
                && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = ip.split(PunctuationConst.COMMA)[0].split(PunctuationConst.COLON)[0];
        }
        return getIpAddress(ip, request);
    }

    /**
     * 获取真实IP。
     *
     * @param request HttpServletRequest。
     * @return 真实IP。
     */
    public static String getIpAddress(final HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (StringUtils.isNotEmpty(ip)
                && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            return ip;
        }
        return getIpAddress(ip, request);
    }

    /**
     * 获取请求发起者的浏览器。
     *
     * @param request HttpServletRequest。
     * @return 浏览器名称。
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    /**
     * 获取真实IP。
     *
     * @param ip      IP地址。
     * @param request HttpServletRequest。
     * @return 真实IP。
     */
    private static String getIpAddress(String ip, final HttpServletRequest request) {
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.equalsAny(ip, LOCALHOST, LOCALHOST_IP, LOCALHOST_IPV6)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return ip;
            }
        }
        return ip;
    }

}
