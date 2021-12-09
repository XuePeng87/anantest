package cn.woyun.anov.gateway.management.config.security;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的鉴权信息。
 *
 * @author xuepeng
 */
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class WebSecurityAuthenticationDetails extends WebAuthenticationDetails {

    /**
     * 构造函数。
     *
     * @param request HttpServletRequest对象。
     */
    public WebSecurityAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }

    /**
     * @return 获取租户主键。
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * 设置租户主键。
     *
     * @param tenantId 租户主键。
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return 获取用户角色。
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * 设置用户角色。
     *
     * @param roles 用户角色。
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * 租户主键。
     */
    private Long tenantId;

    /**
     * 用户角色。
     */
    private Set<String> roles = new HashSet<>();

}
