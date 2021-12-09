package cn.woyun.anov.sdk.mgt.service.user;

import cn.woyun.anov.sdk.mgt.entity.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * <p>
 * 用户的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 创建用户。
     *
     * @param sysUser 用户对象。
     * @return 是否创建成功。
     */
    boolean create(final SysUser sysUser);

    /**
     * 修改用户。
     *
     * @param sysUser 用户对象。
     * @return 是否修改成功。
     */
    boolean update(final SysUser sysUser);

    /**
     * 修改密码。
     *
     * @param id          用户主键。
     * @param oldPassword 旧密码。
     * @param newPassword 新密码。
     * @return 是否修改成功。
     */
    boolean updatePassword(final long id, final String oldPassword, final String newPassword);

    /**
     * 重置密码。
     *
     * @param id 用户主键。
     * @return 是否重置成功。
     */
    boolean resetPassword(final long id);

    /**
     * 根据主键批量删除用户。
     *
     * @param ids 用户主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final Collection<Long> ids);

    /**
     * 检测用户帐号是否存在。
     *
     * @param id      用户主键。
     * @param account 帐号。
     * @return 电话是否存在。
     */
    boolean checkAccountExisted(final long id, final String account);

    /**
     * 检测用户电话是否存在。
     *
     * @param id    用户主键。
     * @param phone 电话。
     * @return 电话是否存在。
     */
    boolean checkPhoneExisted(final long id, final String phone);

    /**
     * 检测用户邮箱是否存在。
     *
     * @param id    用户主键。
     * @param email 邮箱。
     * @return 邮箱是否存在。
     */
    boolean checkEmailExisted(final long id, final String email);

    /**
     * 根据帐号查询用户。
     *
     * @param account 账号。
     * @return 用户。
     */
    SysUser findByAccount(final String account);

    /**
     * 根据帐号密码查询用户。
     *
     * @param account  帐号。
     * @param password 密码。
     * @return 用户。
     */
    boolean findByAccountAndPassword(final String account, final String password);

    /**
     * 根据帐号查询用户的部门。
     *
     * @param account 帐号。
     * @return 部门主键。
     */
    long findDeptIdByAccount(final String account);

    /**
     * 根据条件分页查询用户。
     *
     * @param page    分页。
     * @param sysUser 查询条件。
     * @return 用户集合。
     */
    Page<SysUser> findByPageAndCondition(final Page<SysUser> page, final SysUser sysUser);

    /**
     * 根据部门主键查询部门下的用户数量。
     *
     * @param deptId 部门主键。
     * @return 用户数量。
     */
    int findUserCountByDeptId(final long deptId);

}