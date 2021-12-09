package cn.woyun.anov.sdk.mgt.mapper;

import cn.woyun.anov.sdk.mgt.entity.SysUser;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表  Mapper 接口
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-04
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据帐号查询用户。
     *
     * @param account 帐号。
     * @return 用户。
     */
    @InterceptorIgnore(tenantLine = "1")
    SysUser findByAccount(@Param("account") final String account);

    /**
     * 根据帐号查询用户集合。
     *
     * @param account 帐号。
     * @return 用户集合。
     */
    @InterceptorIgnore(tenantLine = "1")
    List<SysUser> listByAccount(@Param("account") final String account);

    /**
     * 根据邮箱查询用户集合。
     *
     * @param email 邮箱。
     * @return 用户集合。
     */
    @InterceptorIgnore(tenantLine = "1")
    List<SysUser> listByEmail(@Param("email") final String email);

    /**
     * 根据电话号查询用户集合。
     *
     * @param phone 电话号。
     * @return 用户集合。
     */
    @InterceptorIgnore(tenantLine = "1")
    List<SysUser> listByPhone(@Param("phone") final String phone);

}
