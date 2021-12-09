package cn.woyun.anov.sdk.mgt.service.role;

import cn.woyun.anov.sdk.mgt.entity.SysRole;
import cn.woyun.anov.sdk.mgt.enums.RoleStatus;
import cn.woyun.anov.sdk.mgt.exception.SysRoleCannotDeleteException;
import cn.woyun.anov.sdk.mgt.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /**
     * 创建用户。
     *
     * @param sysRole 用户实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysRole sysRole) {
        sysRole.setRoleStatus(RoleStatus.ENABLE.ordinal());
        sysRole.setCreateTime(LocalDateTime.now());
        sysRole.setModifyTime(LocalDateTime.now());
        return super.save(sysRole);
    }

    /**
     * 修改用户。
     *
     * @param sysRole 用户实体类。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysRole sysRole) {
        sysRole.setModifyTime(LocalDateTime.now());
        return super.updateById(sysRole);
    }

    /**
     * 保存角色下的用户。
     *
     * @param id      角色主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveRoleToUser(final long id, final Collection<Long> userIds) {
        return sysRoleUserRelationService.saveRoleToUser(id, userIds);
    }

    /**
     * 保存用户下的角色。
     *
     * @param ids    角色主键集合。
     * @param userId 用户主键。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveUserToRole(final Collection<Long> ids, final long userId) {
        return sysRoleUserRelationService.saveUserToRole(ids, userId);
    }

    /**
     * 保存角色下的功能。
     *
     * @param id      角色主键。
     * @param funcIds 功能主键集合。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveRoleToFunc(final long id, final Collection<Long> funcIds) {
        return sysRoleFuncRelationService.saveRoleToFunc(id, funcIds);
    }

    /**
     * 根据主键删除角色。
     * 角色下有用户，不可删除。
     * 角色下有功能，不可删除。
     *
     * @param ids 角色主键集合。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByIds(final Collection<Long> ids) {
        for (final long id : ids) {
            if (sysRoleUserRelationService.hasUser(id)) {
                throw new SysRoleCannotDeleteException("角色[" + id + "]下有用户，无法删除。");
            }
            if (sysRoleFuncRelationService.hasFunc(id)) {
                throw new SysRoleCannotDeleteException("角色[" + id + "]下有功能，无法删除。");
            }
        }
        return super.removeByIds(ids);
    }

    /**
     * 删除角色下的用户（删除用户时使用）。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteRoleUser(final long userId) {
        return sysRoleUserRelationService.deleteRoleUserByUserId(userId);
    }

    /**
     * 删除角色下的功能（删除用户时使用）。
     *
     * @param funcId 功能主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteRoleFunc(final long funcId) {
        return sysRoleFuncRelationService.deleteRoleFuncByFuncId(funcId);
    }

    /**
     * 查询角色下的用户。
     *
     * @param id 角色主键。
     * @return 用户主键集合。
     */
    @Override
    public List<Long> findRoleUsersById(final long id) {
        return sysRoleUserRelationService.findUserIdsById(id);
    }

    /**
     * 查询角色下的功能。
     *
     * @param id 角色主键。
     * @return 功能主键集合。
     */
    @Override
    public List<Long> findRoleFuncsById(final long id) {
        return sysRoleFuncRelationService.findFuncIdsById(id);
    }

    /**
     * 根据条件分页查询角色。
     *
     * @param page    分页。
     * @param sysRole 查询条件。
     * @return 角色集合。
     */
    @Override
    public Page<SysRole> findByPageAndCondition(final Page<SysRole> page, final SysRole sysRole) {
        final QueryWrapper<SysRole> wrapper = createQueryWrapper(sysRole);
        return super.page(page, wrapper);
    }

    /**
     * 根据用户主键查询该用户拥有的角色。
     *
     * @param userId 用户主键。
     * @return 角色集合。
     */
    @Override
    public List<SysRole> findRolesByUserId(final long userId) {
        // 查询用户拥有的角色主键集合
        final List<Long> roleIds = sysRoleUserRelationService.findRoleIdsByUserId(userId);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            return findByIds(roleIds);
        }
        return new ArrayList<>();
    }

    /**
     * 根据角色主键查询角色拥有的功能主键。
     *
     * @param ids 角色主键集合。
     * @return 功能主键集合。
     */
    @Override
    public List<Long> findFuncIdsByRoleIds(final List<Long> ids) {
        return sysRoleFuncRelationService.findFuncIdsById(ids);
    }

    /**
     * @return 查询全部的业务角色，不包含超级管理员和系统管理员的角色。
     */
    @Override
    public List<SysRole> findBusinessRoles() {
        return super.list(
                createQueryWrapper().lambda().gt(SysRole::getRoleLevel, 1L)
        );
    }

    /**
     * 根据角色主键查询角色。
     *
     * @param ids 角色主键集合。
     * @return 角色集合。
     */
    private List<SysRole> findByIds(final List<Long> ids) {
        final QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRole::getId, ids);
        return super.list(wrapper);
    }

    /**
     * @return 创建QueryWrapper。
     */
    private QueryWrapper<SysRole> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 根据查询条件创建QueryWrapper。
     *
     * @param sysRole 查询条件。
     * @return QueryWrapper对象。
     */
    private QueryWrapper<SysRole> createQueryWrapper(final SysRole sysRole) {
        final QueryWrapper<SysRole> queryWrapper = createQueryWrapper();
        final LambdaQueryWrapper<SysRole> criteria = queryWrapper.lambda();
        if (StringUtils.isNotBlank(sysRole.getRoleName())) {
            criteria.like(SysRole::getRoleName, sysRole.getRoleName());
        }
        if (StringUtils.isNotBlank(sysRole.getRoleCode())) {
            criteria.like(SysRole::getRoleCode, sysRole.getRoleCode());
        }
        criteria.orderByAsc(SysRole::getRoleSort);
        return queryWrapper;
    }

    /**
     * 自动装配用户与角色关系的业务处理接口。
     *
     * @param sysRoleUserRelationService 用户与角色关系的业务处理接口。
     */
    @Autowired
    public void setSysRoleUserRelationService(SysRoleUserRelationService sysRoleUserRelationService) {
        this.sysRoleUserRelationService = sysRoleUserRelationService;
    }

    /**
     * 自动装配用户与功能关系的业务处理接口。
     *
     * @param sysRoleFuncRelationService 用户与功能关系的业务处理接口。
     */
    @Autowired
    public void setSysRoleFuncRelationService(SysRoleFuncRelationService sysRoleFuncRelationService) {
        this.sysRoleFuncRelationService = sysRoleFuncRelationService;
    }

    /**
     * 用户与角色关系的业务处理接口。
     */
    private SysRoleUserRelationService sysRoleUserRelationService;

    /**
     * 用户与功能关系的业务处理接口。
     */
    private SysRoleFuncRelationService sysRoleFuncRelationService;

}