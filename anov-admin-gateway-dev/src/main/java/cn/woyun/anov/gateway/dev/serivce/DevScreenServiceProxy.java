package cn.woyun.anov.gateway.dev.serivce;

import cn.woyun.anov.gateway.dev.bean.resposne.DevScreenResponseBean;

import javax.servlet.http.HttpServletRequest;

/**
 * 大屏客户端的业务处理接口。
 *
 * @author xuepeng
 */
public interface DevScreenServiceProxy {

    /**
     * 认证大屏。
     *
     * @param key     大屏唯一标识。
     * @param request HTTPRequest对象。
     * @return 大屏。
     */
    DevScreenResponseBean verify(final String key, final HttpServletRequest request);

    /**
     * 保存大屏监控图片。
     *
     * @param key  大屏唯一标识。
     * @param url  图片Url。
     * @param page 页面。
     * @return 是否保存成功。
     */
    boolean saveMonitorPic(final String key, final String url, final String page);

}
