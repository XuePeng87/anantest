package cn.woyun.anov.sdk.mgt.service.role;

import cn.woyun.anov.sdk.mgt.entity.SysRoleUserRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色与用户关系的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysRoleUserRelationService extends IService<SysRoleUserRelation> {

    /**
     * 保存角色下的用户。
     *
     * @param id      部门主键。
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
     * 根据用户主键删除角色下的用户。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    boolean deleteRoleUserByUserId(final long userId);

    /**
     * 角色下是否存在用户。
     *
     * @param id 角色主键。
     * @return 是否存在用户。
     */
    boolean hasUser(final long id);

    /**
     * 根据主键查询角色下的用户。
     *
     * @param id 角色主键。
     * @return 角色下的用户主键集合。
     */
    List<Long> findUserIdsById(final long id);

    /**
     * 根据用户主键查询该用户拥有的角色主键。
     *
     * @param userId 用户主键。
     * @return 角色主键集合。
     */
    List<Long> findRoleIdsByUserId(final long userId);

}