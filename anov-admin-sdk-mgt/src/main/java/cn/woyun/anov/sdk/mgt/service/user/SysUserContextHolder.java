package cn.woyun.anov.sdk.mgt.service.user;

import cn.woyun.anov.sdk.mgt.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * 用户的持有器。
 *
 * @author xuepeng
 */
@Component
public interface SysUserContextHolder {

    /**
     * 添加用户。
     *
     * @param sysUser 用户对象。
     */
    void add(final SysUser sysUser);

    /**
     * @return 获取对象。
     */
    SysUser get();

    /**
     * 移除对象。
     */
    void remove();

}
