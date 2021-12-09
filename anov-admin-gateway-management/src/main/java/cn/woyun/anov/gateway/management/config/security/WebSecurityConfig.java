package cn.woyun.anov.gateway.management.config.security;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.bean.request.mgt.LogRequestBean;
import cn.woyun.anov.gateway.management.bean.response.mgt.UserResponseBean;
import cn.woyun.anov.gateway.management.filter.VerifyCodeFilter;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.sdk.mgt.entity.SysLog;
import cn.woyun.anov.sdk.mgt.entity.SysRole;
import cn.woyun.anov.sdk.mgt.entity.SysUser;
import cn.woyun.anov.sdk.mgt.service.log.SysLogService;
import cn.woyun.anov.sdk.mgt.service.user.SysUserContextHolderImpl;
import cn.woyun.anov.web.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 安全配置器。
 *
 * @author xuepeng
 */
@EnableWebSecurity
@Configuration
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO 可配置
    private static final String SUPER_ADMIN = "SUPER_ADMIN";

    /**
     * 安全配置。
     *
     * @param http HttpSecurity。
     * @throws Exception 配置异常。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 开启跨域功能
                .cors()
                // 开启防御CSRF攻击，服务端会在Cookie中写入XSRF-TOKEN
                .and().csrf().csrfTokenRepository(createCookieCsrfTokenRepository()).ignoringAntMatchers(getUrlIgnores())
                // 开启权限控制，配置角色的API权限
                .and().authorizeRequests().antMatchers(getUrlIgnores()).permitAll()
                .antMatchers("/v1/profiles/**", "/v1/devs/**", "/v1/clients/**").hasAnyRole("TENANT_ADMIN", "TENANT_USER")
                .antMatchers("/v1/users/**", "/v1/depts/**", "/v1/logs/**").hasAnyRole("TENANT_ADMIN", SUPER_ADMIN)
                .antMatchers("/**").hasRole(SUPER_ADMIN)
                .anyRequest().authenticated()
                // 自定义登录参数
                .and().formLogin().usernameParameter("userAccount").passwordParameter("userPassword")
                // 自定义鉴权时的请求扩展数据
                .authenticationDetailsSource(new WebSecurityAuthenticationDetailsSource())
                // 设置登录成功的返回数据
                .successHandler(authenticationSuccessHandler())
                // 设置登录失败的返回数据
                .failureHandler(authenticationFailureHandler())
                // 设置登录请求地址，同时验证码过滤器拦截此url
                .loginProcessingUrl(webSecurityUrlProperties.getSignin()).permitAll()
                // 设置登出请求地址，登出时删除Cookies，登出成功返回数据
                .and().logout().logoutUrl(webSecurityUrlProperties.getSignout()).invalidateHttpSession(true)
                // 设置登出成功的返回数据
                .logoutSuccessHandler(logoutSuccessHandler())
                // 登出时清除cookie
                .deleteCookies("XSRF-TOKEN", "SESSION").permitAll()
                // 无权限返回数据
                .and().exceptionHandling()
                // 未登录无权限的返回数据
                .authenticationEntryPoint(authenticationEntryPoint())
                // 已登录无权限的返回数据
                .accessDeniedHandler(accessDeniedHandler())
                // 登录身份超时返回数据
                .and().sessionManagement().invalidSessionStrategy(sessionTimeout())
                // 设置是否只允许一个用户登录
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .expiredSessionStrategy(sessionInformationExpiredStrategy());
        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 认证配置。
     *
     * @param auth 认证管理器。
     * @throws Exception 认证异常。
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 重写userDetailsService
        auth.userDetailsService(securityUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * @return 创建session注册表。
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * @return 密码验证处理器。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return 登入成功处理器。
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            try {
                // 写入租户信息到Session
                final SysUser user = sysUserContextHolder.get();
                final WebSecurityAuthenticationDetails webSecurityAuthenticationDetails = (WebSecurityAuthenticationDetails) authentication.getDetails();
                webSecurityAuthenticationDetails.setTenantId(user.getTenantId());
                for (SysRole role : user.getRoles()) {
                    webSecurityAuthenticationDetails.getRoles().add(role.getRoleCode());
                }
                // 写入登录日志
                try {
                    final LogRequestBean logRequestBean = new LogRequestBean();
                    logRequestBean.setCreateUser(user.getUserAccount());
                    logRequestBean.setCreateTime(LocalDateTime.now());
                    logRequestBean.setTenantId(user.getTenantId());
                    logRequestBean.setLogType("SIGNIN");
                    logRequestBean.setLogSystem("管理系统");
                    logRequestBean.setLogModule("登录");
                    logRequestBean.setLogDescription("用户登录");
                    logRequestBean.setLogIp(WebUtil.getRealIpAddress(httpServletRequest));
                    logRequestBean.setLogReqUrl(httpServletRequest.getRequestURI());
                    logRequestBean.setLogReqBrowser(WebUtil.getBrowser(httpServletRequest));
                    logRequestBean.setLogClassName(this.getClass().getName());
                    logRequestBean.setLogMethodName("Signin");
                    sysLogService.create(BeanUtil.objToObj(logRequestBean, SysLog.class));
                } catch (Exception e) {
                    log.error("save singin log failed, cause is {}", e.getMessage());
                }
                // 查询登录用户的租户信息
                final UserResponseBean currentUser = BeanUtil.objToObj(user, UserResponseBean.class);
                writeJson(httpServletResponse, BeanUtil.getObjToStr(
                        DefaultHttpResultFactory.success("登录成功。", currentUser))
                );
            } finally {
                sysUserContextHolder.remove();
            }
        };
    }

    /**
     * @return 登录失败处理器。
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (httpServletRequest, httpServletResponse, authenticationException) ->
                writeJson(httpServletResponse, BeanUtil.getObjToStr(DefaultHttpResultFactory.fail("登录失败。")));
    }

    /**
     * @return 登出成功处理器。
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            try {
                final LogRequestBean logRequestBean = new LogRequestBean();
                logRequestBean.setCreateUser(authentication.getName());
                logRequestBean.setCreateTime(LocalDateTime.now());
                logRequestBean.setTenantId(((WebSecurityAuthenticationDetails) authentication.getDetails()).getTenantId());
                logRequestBean.setLogType("SIGNOUT");
                logRequestBean.setLogSystem("管理系统");
                logRequestBean.setLogModule("登出");
                logRequestBean.setLogDescription("用户登出");
                logRequestBean.setLogIp(WebUtil.getRealIpAddress(httpServletRequest));
                logRequestBean.setLogReqUrl(httpServletRequest.getRequestURI());
                logRequestBean.setLogReqBrowser(WebUtil.getBrowser(httpServletRequest));
                logRequestBean.setLogClassName(this.getClass().getName());
                logRequestBean.setLogMethodName("SignOut");
                sysLogService.create(BeanUtil.objToObj(logRequestBean, SysLog.class));
            } catch (Exception e) {
                log.error("save signout log failed, cause is {}", e.getMessage());
            }
            writeJson(httpServletResponse, BeanUtil.getObjToStr(DefaultHttpResultFactory.success("登出成功。")));
        };
    }

    /**
     * @return 未登录，无权限操作的处理器。
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) ->
                writeJson(
                        httpServletResponse,
                        BeanUtil.getObjToStr(DefaultHttpResultFactory.permissions("未登录无法操作。"))
                );
    }

    /**
     * @return 登录后，无权限操作的处理器。
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, accessDeniedException) ->
                writeJson(
                        httpServletResponse,
                        BeanUtil.getObjToStr(DefaultHttpResultFactory.permissions("无权限操作。"))
                );
    }

    /**
     * @return 登录信息超时策略。
     */
    @Bean
    public InvalidSessionStrategy sessionTimeout() {
        return (httpServletRequest, httpServletResponse) -> {
            // 清空XSRF-TOKEN
            Cookie xsrfTokenCookie = new Cookie("XSRF-TOKEN", null);
            xsrfTokenCookie.setMaxAge(0);
            xsrfTokenCookie.setPath(httpServletRequest.getContextPath() + "/");
            httpServletResponse.addCookie(xsrfTokenCookie);
            // 清空SESSION
            Cookie sessionCookie = new Cookie("SESSION", null);
            sessionCookie.setMaxAge(0);
            sessionCookie.setPath(httpServletRequest.getContextPath() + "/");
            httpServletResponse.addCookie(sessionCookie);
            // 返回登录超时的数据
            writeJson(
                    httpServletResponse,
                    BeanUtil.getObjToStr(DefaultHttpResultFactory.timeout("登录超时。"))
            );
        };
    }

    /**
     * @return 用户重复登录。
     */
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return event -> writeJson(
                event.getResponse(),
                BeanUtil.getObjToStr(DefaultHttpResultFactory.timeout("用户重复登录。"))
        );
    }

    /**
     * 写入JSON到响应流。
     *
     * @param response HttpServletResponse。
     * @param json     内容。
     */
    private void writeJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(json);
            out.flush();
        }
    }

    /**
     * 获取忽略验证的Url。
     *
     * @return 忽略验证的Url。
     */
    private String[] getUrlIgnores() {
        List<String> ignoreList = new ArrayList<>();
        ignoreList.add(webSecurityUrlProperties.getSignin());
        if (webSecurityUrlProperties.getIgnoreUrls() != null) {
            ignoreList.addAll(webSecurityUrlProperties.getIgnoreUrls());
        }
        String[] ignores = new String[ignoreList.size()];
        return ignoreList.toArray(ignores);
    }

    /**
     * @return 创建CSRF的Cookie存储对象。
     */
    private CookieCsrfTokenRepository createCookieCsrfTokenRepository() {
        CookieCsrfTokenRepository result = CookieCsrfTokenRepository.withHttpOnlyFalse();
        result.setCookiePath("/");
        return result;
    }

    /**
     * 自动装配SpringSecurity所需的Url配置类。
     *
     * @param webSecurityUrlProperties SpringSecurity所需的Url配置类。
     */
    @Autowired
    public void setWebSecurityUrlProperties(WebSecurityUrlProperties webSecurityUrlProperties) {
        this.webSecurityUrlProperties = webSecurityUrlProperties;
    }

    /**
     * 自动装配用户认证的服务类。
     *
     * @param securityUserDetailsService 用户认证的服务类。
     */
    @Autowired
    public void setSecurityUserDetailsService(WebSecurityUserDetailsService securityUserDetailsService) {
        this.securityUserDetailsService = securityUserDetailsService;
    }

    /**
     * 自动装配用户的线程持有器。
     *
     * @param sysUserContextHolder 用户的线程持有器。
     */
    @Autowired
    public void setSysUserContextHolder(SysUserContextHolderImpl sysUserContextHolder) {
        this.sysUserContextHolder = sysUserContextHolder;
    }

    /**
     * 自动装配日志操作的业务处理接口。
     *
     * @param sysLogService 日志操作的业务处理接口。
     */
    @Autowired
    public void setSysLogService(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 自动装配验证码的验证过滤器。
     *
     * @param verifyCodeFilter 验证码的验证过滤器。
     */
    @Autowired
    public void setVerifyCodeFilter(VerifyCodeFilter verifyCodeFilter) {
        this.verifyCodeFilter = verifyCodeFilter;
    }

    /**
     * SpringSecurity所需的Url配置类。
     */
    private WebSecurityUrlProperties webSecurityUrlProperties;
    /**
     * 用户认证的服务类。
     */
    private WebSecurityUserDetailsService securityUserDetailsService;
    /**
     * 用户的线程持有器。
     */
    private SysUserContextHolderImpl sysUserContextHolder;
    /**
     * 日志操作的业务处理接口。
     */
    private SysLogService sysLogService;
    /**
     * 验证码的验证过滤器。
     */
    private VerifyCodeFilter verifyCodeFilter;

}
