package cn.woyun.anov.sdk.mgt.service.user;

import cn.woyun.anov.sdk.mgt.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * 用户的持有器实现类。
 *
 * @author xuepeng
 */
@Component
public class SysUserContextHolderImpl implements SysUserContextHolder {

    private static final ThreadLocal<SysUser> HOLDER = new ThreadLocal<>();

    /**
     * 添加用户。
     *
     * @param sysUser 用户对象。
     */
    @Override
    public void add(final SysUser sysUser) {
        HOLDER.set(sysUser);
    }

    /**
     * @return 获取对象。
     */
    @Override
    public SysUser get() {
        return HOLDER.get();
    }

    /**
     * 移除对象。
     */
    @Override
    public void remove() {
        HOLDER.remove();
    }

}
