package cn.woyun.anov.sdk.mgt.service.role;

import cn.woyun.anov.sdk.mgt.entity.SysRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 创建用户。
     *
     * @param sysRole 用户实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysRole sysRole);

    /**
     * 修改用户。
     *
     * @param sysRole 用户实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysRole sysRole);

    /**
     * 保存角色下的用户。
     *
     * @param id      角色主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    boolean saveRoleToUser(final long id, final Collection<Long> userIds);

    /**
     * 保存用户下的角色。
     *
     * @param ids    角色主键集合。
     * @param userId 用户主键。
     * @return 是否保存成功。
     */
    boolean saveUserToRole(final Collection<Long> ids, final long userId);

    /**
     * 保存角色下的功能。
     *
     * @param id      角色主键。
     * @param funcIds 功能主键集合。
     * @return 是否保存成功。
     */
    boolean saveRoleToFunc(final long id, final Collection<Long> funcIds);

    /**
     * 根据主键删除角色。
     *
     * @param ids 角色主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final Collection<Long> ids);

    /**
     * 删除角色下的用户（删除用户时使用）。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    boolean deleteRoleUser(final long userId);

    /**
     * 删除角色下的功能（删除功能时使用）。
     *
     * @param funcId 功能主键。
     * @return 是否删除成功。
     */
    boolean deleteRoleFunc(final long funcId);

    /**
     * 查询角色下的用户。
     *
     * @param id 角色主键。
     * @return 用户主键集合。
     */
    List<Long> findRoleUsersById(final long id);

    /**
     * 查询角色下的功能。
     *
     * @param id 角色主键。
     * @return 功能主键集合。
     */
    List<Long> findRoleFuncsById(final long id);

    /**
     * 根据条件分页查询角色。
     *
     * @param page    分页。
     * @param sysRole 查询条件。
     * @return 角色集合。
     */
    Page<SysRole> findByPageAndCondition(final Page<SysRole> page, final SysRole sysRole);

    /**
     * 根据用户主键查询该用户拥有的角色。
     *
     * @param userId 用户主键。
     * @return 角色集合。
     */
    List<SysRole> findRolesByUserId(final long userId);

    /**
     * 根据角色主键查询角色拥有的功能主键。
     *
     * @param ids 角色主键集合。
     * @return 功能主键集合。
     */
    List<Long> findFuncIdsByRoleIds(final List<Long> ids);

    /**
     * @return 查询全部的业务角色。
     */
    List<SysRole> findBusinessRoles();

}