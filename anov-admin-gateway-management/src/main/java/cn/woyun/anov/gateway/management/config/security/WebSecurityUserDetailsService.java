package cn.woyun.anov.gateway.management.config.security;

import cn.woyun.anov.sdk.mgt.entity.SysRole;
import cn.woyun.anov.sdk.mgt.entity.SysUser;
import cn.woyun.anov.sdk.mgt.service.user.SysUserContextHolderImpl;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用户信息详情服务。
 *
 * @author xuepeng
 */
@Service("userDetailsService")
public class WebSecurityUserDetailsService implements UserDetailsService {

    /**
     * 根据账号加载用户信息。
     *
     * @param userAccount 账号。
     * @return 用户信息。
     * @throws UsernameNotFoundException 用户不存在异常。
     */
    @Override
    public UserDetails loadUserByUsername(String userAccount) {
        // 根据帐号查询用户
        final SysUser user = sysUserService.findByAccount(userAccount);
        if (user == null) {
            throw new BadCredentialsException("登录失败，用户[" + userAccount + "]不存在");
        }
        // 设置角色，角色都以ROLE_开头
        final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (SysRole role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleCode());
            grantedAuthorities.add(grantedAuthority);
        }
        sysUserContextHolder.add(user);
        // 用户名密码构造User
        return new User(userAccount, user.getUserPassword(), grantedAuthorities);
    }

    /**
     * 自动装配用户的业务处理接口。
     *
     * @param sysUserService 用户的业务处理接口。
     */
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
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
     * 用户的业务处理接口。
     */
    private SysUserService sysUserService;

    /**
     * 用户的线程持有器。
     */
    private SysUserContextHolderImpl sysUserContextHolder;

}
