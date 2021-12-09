package cn.woyun.anov.sdk.mgt.service.user;

import cn.woyun.anov.biz.ExistsUtil;
import cn.woyun.anov.sdk.mgt.entity.*;
import cn.woyun.anov.sdk.mgt.exception.*;
import cn.woyun.anov.sdk.mgt.mapper.SysUserMapper;
import cn.woyun.anov.sdk.mgt.service.dept.SysDeptService;
import cn.woyun.anov.sdk.mgt.service.func.SysFuncService;
import cn.woyun.anov.sdk.mgt.service.role.SysRoleService;
import cn.woyun.anov.sdk.mgt.service.tenant.SysTenantService;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 创建用户。
     *
     * @param sysUser 用户对象。
     * @return 是否创建成功。
     */
    @Override
    @Transactional
    public boolean create(final SysUser sysUser) {
        // 创建用户
        if (checkAccountExisted(0L, sysUser.getUserAccount())) {
            throw new SysUserAccountDuplicateException("用户帐号已存在。");
        }
        if (checkPhoneExisted(0L, sysUser.getUserPhone())) {
            throw new SysUserPhoneDuplicateException("用户电话号已存在。");
        }
        if (checkEmailExisted(0L, sysUser.getUserEmail())) {
            throw new SysUserEmailDuplicateException("用户邮箱已存在。");
        }
        sysUser.setUserPassword(sysUserPasswordHandler.generate());
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setModifyTime(LocalDateTime.now());
        super.save(sysUser);
        // 创建用户和角色的关系
        final List<Long> roleIds = sysUser.getRoles().stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());
        return sysRoleService.saveUserToRole(roleIds, sysUser.getId());
    }

    /**
     * 修改用户。
     *
     * @param sysUser 用户对象。
     * @return 是否修改成功。
     */
    @Override
    @Transactional
    public boolean update(final SysUser sysUser) {
        if (checkAccountExisted(sysUser.getId(), sysUser.getUserAccount())) {
            throw new SysUserAccountDuplicateException("用户帐号已存在。");
        }
        if (checkPhoneExisted(sysUser.getId(), sysUser.getUserPhone())) {
            throw new SysUserPhoneDuplicateException("用户电话号已存在。");
        }
        if (checkEmailExisted(sysUser.getId(), sysUser.getUserEmail())) {
            throw new SysUserEmailDuplicateException("用户邮箱已存在。");
        }
        sysUser.setModifyTime(LocalDateTime.now());
        super.updateById(sysUser);
        // 创建用户和角色的关系
        final List<Long> roleIds = sysUser.getRoles().stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());
        return sysRoleService.saveUserToRole(roleIds, sysUser.getId());
    }

    /**
     * 修改密码。
     *
     * @param id          用户主键。
     * @param oldPassword 旧密码。
     * @param newPassword 新密码。
     * @return 是否修改成功。
     */
    @Override
    public boolean updatePassword(final long id, final String oldPassword, final String newPassword) {
        // 验证旧密码是否正确
        final SysUser user = super.getById(id);
        if (!sysUserPasswordHandler.matches(oldPassword, user.getUserPassword())) {
            throw new SysUserOldPasswordIncorrectException("旧密码不正确。");
        }
        // 更新密码。
        final SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setUserPassword(sysUserPasswordHandler.encode(newPassword));
        sysUser.setModifyTime(LocalDateTime.now());
        return this.updateById(sysUser);
    }

    /**
     * 重置密码。
     *
     * @param id 用户主键。
     * @return 是否重置成功。
     */
    @Override
    public boolean resetPassword(final long id) {
        final SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setUserPassword(sysUserPasswordHandler.generate());
        sysUser.setModifyTime(LocalDateTime.now());
        return super.updateById(sysUser);
    }

    /**
     * 根据主键批量删除用户。
     * 删除用户与部门的关系。
     * 删除用户与角色的关系。
     *
     * @param ids 用户主键集合。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteByIds(final Collection<Long> ids) {
        for (final long id : ids) {
            // 删除用户与角色的关系
            sysRoleService.deleteRoleUser(id);
        }
        // 删除用户
        return super.removeByIds(ids);
    }

    /**
     * 根据帐号查询用户。
     *
     * @param account 账号。
     * @return 用户。
     */
    @Override
    public SysUser findByAccount(final String account) {
        final SysUser sysUser = super.getBaseMapper().findByAccount(account);
        if (Objects.isNull(sysUser)) {
            throw new SysUserNotFoundException("根据账号[" + account + "]未能查询到用户");
        }
        // 查询用户拥有的角色
        final List<SysRole> sysRoles = sysRoleService.findRolesByUserId(sysUser.getId());
        sysUser.setRoles(sysRoles);
        // 查询用户拥有的角色的功能合集
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            final List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
            final List<Long> funcIds = sysRoleService.findFuncIdsByRoleIds(roleIds);
            final List<SysFunc> funcs = sysFuncService.findByIds(funcIds);
            sysUser.setFuncs(funcs);
        }
        // 查询用户拥有的部门
        final SysDept sysDept = sysDeptService.findById(sysUser.getDeptId());
        sysUser.setDept(sysDept);
        // 查询用户所属的租户
        final SysTenant sysTenant = sysTenantService.findById(sysUser.getTenantId());
        sysUser.setTenant(sysTenant);
        return sysUser;
    }

    /**
     * 根据帐号密码查询用户。
     *
     * @param account  帐号。
     * @param password 密码。
     * @return 用户。
     */
    public boolean findByAccountAndPassword(final String account, final String password) {
        final SysUser sysUser = super.getBaseMapper().findByAccount(account);
        if(Objects.isNull(sysUser)) {
            return Boolean.FALSE;
        }
        if (!sysUserPasswordHandler.matches(password, sysUser.getUserPassword())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 根据帐号查询用户的部门。
     *
     * @param account 帐号。
     * @return 部门主键。
     */
    @Override
    public long findDeptIdByAccount(final String account) {
        final SysUser sysUser = super.getBaseMapper().findByAccount(account);
        if (Objects.isNull(sysUser)) {
            throw new SysUserNotFoundException("根据账号[" + account + "]未能查询到用户");
        }
        return sysUser.getDeptId();
    }

    /**
     * 根据条件分页查询用户。
     *
     * @param page    分页。
     * @param sysUser 查询条件。
     * @return 用户集合。
     */
    @Override
    public Page<SysUser> findByPageAndCondition(final Page<SysUser> page, final SysUser sysUser) {
        final QueryWrapper<SysUser> wrapper = createQueryWrapper(sysUser);
        Page<SysUser> result = super.page(page, wrapper);
        final Map<Long, String> deptMap = sysDeptService.findAllToMap();
        for (final SysUser user : result.getRecords()) {
            if (deptMap.containsKey(user.getDeptId())) {
                user.setDeptName(deptMap.get(user.getDeptId()));
            }
        }
        return result;
    }

    /**
     * 检测用户帐号是否存在。
     *
     * @param id      用户主键。
     * @param account 帐号。
     * @return 帐号是否存在。
     */
    @Override
    public boolean checkAccountExisted(final long id, final String account) {
        final List<SysUser> sysUsers = super.getBaseMapper().listByAccount(account);
        return ExistsUtil.exists(sysUsers, id == 0L ? StringUtils.EMPTY : String.valueOf(id), "Id");
    }

    /**
     * 检测用户电话号是否存在。
     *
     * @param id    用户主键。
     * @param phone 电话号。
     * @return 电话号是否存在。
     */
    @Override
    public boolean checkPhoneExisted(final long id, final String phone) {
        final List<SysUser> sysUsers = super.getBaseMapper().listByPhone(phone);
        return ExistsUtil.exists(sysUsers, id == 0L ? StringUtils.EMPTY : String.valueOf(id), "Id");
    }

    /**
     * 检测用户邮箱是否存在。
     *
     * @param id    用户主键。
     * @param email 邮箱。
     * @return 邮箱是否存在。
     */
    @Override
    public boolean checkEmailExisted(final long id, final String email) {
        final List<SysUser> sysUsers = super.getBaseMapper().listByEmail(email);
        return ExistsUtil.exists(sysUsers, id == 0L ? StringUtils.EMPTY : String.valueOf(id), "Id");
    }

    /**
     * 根据部门主键查询部门下的用户数量。
     *
     * @param deptId 部门主键。
     * @return 用户数量。
     */
    @Override
    public int findUserCountByDeptId(final long deptId) {
        return super.count(
                createQueryWrapper().lambda().eq(SysUser::getDeptId, deptId)
        );
    }

    /**
     * @return 创建QueryWrapper。
     */
    private QueryWrapper<SysUser> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 根据查询条件创建QueryWrapper。
     *
     * @param sysUser 查询条件。
     * @return QueryWrapper对象。
     */
    private QueryWrapper<SysUser> createQueryWrapper(final SysUser sysUser) {
        final QueryWrapper<SysUser> queryWrapper = createQueryWrapper();
        final LambdaQueryWrapper<SysUser> criteria = queryWrapper.lambda();
        if (StringUtils.isNotBlank(sysUser.getUserAccount())) {
            criteria.like(SysUser::getUserAccount, sysUser.getUserAccount());
        }
        if (StringUtils.isNotBlank(sysUser.getUserName())) {
            criteria.like(SysUser::getUserName, sysUser.getUserName());
        }
        if (StringUtils.isNotBlank(sysUser.getUserPhone())) {
            criteria.like(SysUser::getUserPhone, sysUser.getUserPhone());
        }
        if (StringUtils.isNotBlank(sysUser.getUserEmail())) {
            criteria.like(SysUser::getUserEmail, sysUser.getUserEmail());
        }
        if (!Objects.isNull(sysUser.getUserStatus())) {
            criteria.eq(SysUser::getUserStatus, sysUser.getUserStatus());
        }
        criteria.orderByDesc(SysUser::getCreateTime);
        return queryWrapper;
    }

    /**
     * 自动装配角色的业务处理接口。
     *
     * @param sysRoleService 角色的业务处理接口。
     */
    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 自动装配部门的业务处理接口。
     *
     * @param sysDeptService 部门的业务处理接口。
     */
    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    /**
     * 自动装配功能的业务处理接口。
     *
     * @param sysFuncService 功能的业务处理接口。
     */
    @Autowired
    public void setSysFuncService(SysFuncService sysFuncService) {
        this.sysFuncService = sysFuncService;
    }

    /**
     * 自动装配租户的业务处理接口。
     *
     * @param sysTenantService 租户的业务处理接口。
     */
    @Autowired
    public void setSysTenantService(SysTenantService sysTenantService) {
        this.sysTenantService = sysTenantService;
    }

    /**
     * 自动装配用户密码处理器。
     *
     * @param sysUserPasswordHandler 用户密码处理器。
     */
    @Autowired
    public void setSysUserPasswordHandler(SysUserPasswordHandler sysUserPasswordHandler) {
        this.sysUserPasswordHandler = sysUserPasswordHandler;
    }

    /**
     * 角色的业务处理接口。
     */
    private SysRoleService sysRoleService;

    /**
     * 部门的业务处理接口。
     */
    private SysDeptService sysDeptService;

    /**
     * 功能的业务处理接口。
     */
    private SysFuncService sysFuncService;

    /**
     * 租户的业务处理接口。
     */
    private SysTenantService sysTenantService;

    /**
     * 用户密码处理器。
     */
    private SysUserPasswordHandler sysUserPasswordHandler;

}