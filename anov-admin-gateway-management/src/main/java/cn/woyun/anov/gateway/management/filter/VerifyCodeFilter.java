package cn.woyun.anov.gateway.management.filter;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.sdk.cpsp.verify.VerifyCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class VerifyCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals(request.getRequestURI(), "/management/signin")) {
            final String uuid = request.getParameter("uuid");
            final String code = request.getParameter("code");
            try {
                if (!verifyCodeService.validate(uuid, code)) {
                    writeJson(response, BeanUtil.getObjToStr(DefaultHttpResultFactory.fail("验证码错误。")));
                    return;
                }
            } catch (IllegalArgumentException e) {
                writeJson(response, BeanUtil.getObjToStr(DefaultHttpResultFactory.fail("验证码不存在或已过期。")));
                return;
            }
        }
        filterChain.doFilter(request, response);
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
        }
    }

    /**
     * 自动装配验证码的业务处理接口。
     *
     * @param verifyCodeService 验证码的业务处理接口。
     */
    @Autowired
    public void setVerifyCodeService(VerifyCodeService verifyCodeService) {
        this.verifyCodeService = verifyCodeService;
    }

    /**
     * 验证码的业务处理接口。
     */
    private VerifyCodeService verifyCodeService;


}
