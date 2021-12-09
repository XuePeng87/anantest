package cn.woyun.anov.sdk.mgt.service.tenant;

import cn.woyun.anov.biz.ExistsUtil;
import cn.woyun.anov.config.mgt.SysTenantConfiguration;
import cn.woyun.anov.sdk.mgt.entity.SysDept;
import cn.woyun.anov.sdk.mgt.entity.SysRole;
import cn.woyun.anov.sdk.mgt.entity.SysTenant;
import cn.woyun.anov.sdk.mgt.entity.SysUser;
import cn.woyun.anov.sdk.mgt.enums.*;
import cn.woyun.anov.sdk.mgt.exception.SysTenantEmailDuplicateException;
import cn.woyun.anov.sdk.mgt.exception.SysTenantPhoneDuplicateException;
import cn.woyun.anov.sdk.mgt.mapper.SysTenantMapper;
import cn.woyun.anov.sdk.mgt.service.dept.SysDeptService;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 租户的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    /**
     * 创建租户。
     * 创建时分为两种情况，自主注册和超管创建。
     * 自主注册时，注册的用户的角色为试用人员，租户级别是试用。
     * 超管注册时，注册的用户的角色为选择的角色，租户级别也是选择的级别。
     *
     * @param sysTenant 租户对象。
     * @return 是否创建成功。
     */
    @Override
    @Transactional
    public boolean create(final SysTenant sysTenant) {
        if (checkPhoneExisted(0L, sysTenant.getTenantPhone())) {
            throw new SysTenantPhoneDuplicateException("租户电话号已存在。");
        }
        if (checkEmailExisted(0L, sysTenant.getTenantEmail())) {
            throw new SysTenantEmailDuplicateException("租户邮箱已存在。");
        }
        // 创建租户
        if (Objects.isNull(sysTenant.getTenantLevel())) {
            sysTenant.setTenantLevel(TenantLevel.TRIAL.ordinal());
        }
        sysTenant.setTenantStatus(TenantStatus.ENABLE.ordinal());
        sysTenant.setTenantExpireTime(LocalDateTime.now().plusDays(
                sysTenantConfiguration.getExpiration().toDays()
        ));
        sysTenant.setCreateUser(sysTenantConfiguration.getCreateUser());
        sysTenant.setCreateTime(LocalDateTime.now());
        sysTenant.setModifyUser(sysTenantConfiguration.getCreateUser());
        sysTenant.setModifyTime(LocalDateTime.now());
        super.save(sysTenant);
        // 如果是超管创建的租户，设置租户与角色的关系
        if (sysTenant.getTenantOrigin() == TenantOrigin.SYSTEM.ordinal()) {
            sysTenantRoleRelationService.saveTenantToRole(sysTenant.getId(), sysTenant.getRoleIds());
        }
        // 创建部门
        final SysDept dept = createDeptEntity(sysTenant);
        sysDeptService.create(dept);
        // 创建用户
        final SysUser user = createUserEntity(sysTenant, dept.getId());
        return sysUserService.create(user);
    }

    /**
     * 修改租户。
     *
     * @param sysTenant 租户对象。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysTenant sysTenant) {
        if (checkPhoneExisted(sysTenant.getId(), sysTenant.getTenantPhone())) {
            throw new SysTenantPhoneDuplicateException("租户电话号已存在。");
        }
        if (checkEmailExisted(sysTenant.getId(), sysTenant.getTenantEmail())) {
            throw new SysTenantEmailDuplicateException("租户邮箱已存在。");
        }
        sysTenant.setModifyUser(sysTenantConfiguration.getCreateUser());
        sysTenant.setModifyTime(LocalDateTime.now());
        // 修改租户与角色的关系
        sysTenantRoleRelationService.saveTenantToRole(sysTenant.getId(), sysTenant.getRoleIds());
        return super.updateById(sysTenant);
    }

    /**
     * 根据主键批量删除租户。
     *
     * @param ids 组件集合。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByIds(final Collection<Long> ids) {
        final List<SysTenant> tenants = new ArrayList<>();
        for (long id : ids) {
            final SysTenant sysTenant = new SysTenant();
            sysTenant.setId(id);
            sysTenant.setDeleted(Boolean.TRUE);
            tenants.add(sysTenant);
        }
        return super.updateBatchById(tenants);
    }

    /**
     * 检测租户电话是否存在。
     *
     * @param id    租户主键。
     * @param phone 电话。
     * @return 电话是否存在。
     */
    @Override
    public boolean checkPhoneExisted(final long id, final String phone) {
        final List<SysTenant> sysTenants = super.list(
                createQueryWrapper().lambda().eq(SysTenant::getTenantPhone, phone)
        );
        return ExistsUtil.exists(sysTenants, id == 0 ? StringUtils.EMPTY : String.valueOf(id), "Id");
    }

    /**
     * 检测租户邮箱是否存在。
     *
     * @param id    租户主键。
     * @param email 邮箱。
     * @return 邮箱是否存在。
     */
    @Override
    public boolean checkEmailExisted(final long id, final String email) {
        final List<SysTenant> sysTenants = super.list(
                createQueryWrapper().lambda().eq(SysTenant::getTenantEmail, email)
        );
        return ExistsUtil.exists(sysTenants, id == 0 ? StringUtils.EMPTY : String.valueOf(id), "Id");
    }

    /**
     * 根据主键查询租户。
     *
     * @param id 租户主键。
     * @return 租户。
     */
    @Override
    public SysTenant findById(final long id) {
        return super.getById(id);
    }

    /**
     * 根据条件分页查询。
     *
     * @param page      分页信息。
     * @param sysTenant 查询条件。
     * @return 租户集合。
     */
    @Override
    public Page<SysTenant> findByPageAndCondition(final Page<SysTenant> page, final SysTenant sysTenant) {
        return super.page(page, createQueryWrapper(sysTenant));
    }

    /**
     * 创建租户时，自动创建一个部门。
     *
     * @param sysTenant 租户对象。
     * @return 部门。
     */
    private SysDept createDeptEntity(final SysTenant sysTenant) {
        final SysDept sysDept = new SysDept();
        sysDept.setTenantId(sysTenant.getId());
        sysDept.setPid(0L);
        sysDept.setDeptName(sysTenant.getTenantName());
        sysDept.setDeptSort(100);
        sysDept.setDeptStatus(DeptStatus.ENABLE.ordinal());
        sysDept.setCreateUser(sysTenantConfiguration.getCreateUser());
        sysDept.setModifyUser(sysTenantConfiguration.getCreateUser());
        return sysDept;
    }

    /**
     * 创建租户时，自动创建一个用户。
     *
     * @param sysTenant 租户对象。
     * @param deptId    部门主键。
     * @return 用户。
     */
    private SysUser createUserEntity(final SysTenant sysTenant, final long deptId) {
        final SysUser sysUser = new SysUser();
        sysUser.setUserAccount(sysTenant.getUserAccount());
        sysUser.setUserName("管理员");
        sysUser.setTenantId(sysTenant.getId());
        sysUser.setUserStatus(UserStatus.ENABLE.ordinal());
        sysUser.setUserPhone(sysTenant.getTenantPhone());
        sysUser.setUserEmail(sysTenant.getTenantEmail());
        sysUser.setCreateUser(sysTenantConfiguration.getCreateUser());
        sysUser.setModifyUser(sysTenantConfiguration.getCreateUser());
        // 判断是自主注册的试用用户，还是系统管理员创建的用户
        if (sysTenant.getTenantOrigin() == TenantOrigin.CUSTOMER.ordinal()) {
            final SysRole admin = new SysRole();
            admin.setId(3L);
            final SysRole operator = new SysRole();
            operator.setId(4L);
            sysUser.getRoles().add(admin);
            sysUser.getRoles().add(operator);
        } else {
            // 如果是超级管理员创建的用户，角色授权为选择的角色
            for (final long roleId : sysTenant.getRoleIds()) {
                final SysRole role = new SysRole();
                role.setId(roleId);
                sysUser.getRoles().add(role);
            }
        }
        sysUser.setDeptId(deptId);
        return sysUser;
    }

    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<SysTenant> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 根据条件创建QueryWrapper。
     *
     * @param sysTenant 租户对象。
     * @return QueryWrapper对象。
     */
    private QueryWrapper<SysTenant> createQueryWrapper(final SysTenant sysTenant) {
        final QueryWrapper<SysTenant> wrapper = createQueryWrapper();
        final LambdaQueryWrapper<SysTenant> criteria = wrapper.lambda();
        if (StringUtils.isNotBlank(sysTenant.getTenantName())) {
            criteria.like(SysTenant::getTenantName, sysTenant.getTenantName());
        }
        if (StringUtils.isNotBlank(sysTenant.getTenantPhone())) {
            criteria.like(SysTenant::getTenantPhone, sysTenant.getTenantPhone());
        }
        if (StringUtils.isNotBlank(sysTenant.getTenantEmail())) {
            criteria.like(SysTenant::getTenantEmail, sysTenant.getTenantEmail());
        }
        if (Objects.nonNull(sysTenant.getTenantLevel())) {
            criteria.eq(SysTenant::getTenantLevel, sysTenant.getTenantLevel());
        }
        if (Objects.nonNull(sysTenant.getTenantStatus())) {
            criteria.eq(SysTenant::getTenantStatus, sysTenant.getTenantStatus());
        }
        if (Objects.nonNull(sysTenant.getTenantOrigin())) {
            criteria.eq(SysTenant::getTenantOrigin, sysTenant.getTenantOrigin());
        }
        if (ObjectUtils.allNotNull(sysTenant.getBeginTime(), sysTenant.getEndTime())) {
            criteria.between(SysTenant::getCreateTime, sysTenant.getBeginTime(), sysTenant.getEndTime());
        }
        criteria.eq(SysTenant::getDeleted, Boolean.FALSE);
        criteria.orderByDesc(SysTenant::getCreateTime);
        return wrapper;
    }

    /**
     * 自动装配用户的业务接口。
     *
     * @param sysUserService 用户的业务接口。
     */
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
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
     * 自动装配租户与角色关系的业务处理接口。
     *
     * @param sysTenantRoleRelationService 租户与角色关系的业务处理接口。
     */
    @Autowired
    public void setSysTenantRoleRelationService(SysTenantRoleRelationService sysTenantRoleRelationService) {
        this.sysTenantRoleRelationService = sysTenantRoleRelationService;
    }

    /**
     * 自动装配租户管理的配置信息。
     *
     * @param sysTenantConfiguration 租户管理的配置信息。
     */
    @Autowired
    public void setSysTenantConfiguration(SysTenantConfiguration sysTenantConfiguration) {
        this.sysTenantConfiguration = sysTenantConfiguration;
    }

    /**
     * 用户的业务处理接口。
     */
    private SysUserService sysUserService;

    /**
     * 部门的业务处理接口。
     */
    private SysDeptService sysDeptService;

    /**
     * 租户与角色关系的业务处理接口。
     */
    private SysTenantRoleRelationService sysTenantRoleRelationService;

    /**
     * 租户管理的配置信息。
     */
    private SysTenantConfiguration sysTenantConfiguration;

}