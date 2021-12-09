package cn.woyun.anov.gateway.management.config.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义鉴权信息源。
 *
 * @author xuepeng
 */
public class WebSecurityAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebSecurityAuthenticationDetails> {

    /**
     * 构建鉴权信息。
     *
     * @param httpServletRequest HttpServletRequest对象。
     * @return 自定义的鉴权信息。
     */
    @Override
    public WebSecurityAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {
        return new WebSecurityAuthenticationDetails(httpServletRequest);
    }

}
