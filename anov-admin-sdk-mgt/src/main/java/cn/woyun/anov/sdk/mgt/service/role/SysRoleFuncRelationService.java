package cn.woyun.anov.sdk.mgt.service.role;

import cn.woyun.anov.sdk.mgt.entity.SysRoleFuncRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色与功能关系的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysRoleFuncRelationService extends IService<SysRoleFuncRelation> {

    /**
     * 保存角色下的功能。
     *
     * @param id      部门主键。
     * @param funcIds 功能主键集合。
     * @return 是否保存成功。
     */
    boolean saveRoleToFunc(final long id, final Collection<Long> funcIds);

    /**
     * 根据功能主键删除角色下的功能。
     *
     * @param funcId 功能主键。
     * @return 是否删除成功。
     */
    boolean deleteRoleFuncByFuncId(final long funcId);

    /**
     * 角色下是否存在功能。
     *
     * @param id 功能主键。
     * @return 是否存在功能。
     */
    boolean hasFunc(final long id);

    /**
     * 根据主键查询角色下的功能。
     *
     * @param id 角色主键。
     * @return 角色下的功能主键集合。
     */
    List<Long> findFuncIdsById(final long id);

    /**
     * 根据主键查询角色下的功能。
     *
     * @param id 角色主键集合。
     * @return 角色下的功能主键集合。
     */
    List<Long> findFuncIdsById(final List<Long> id);

}