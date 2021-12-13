package cn.woyun.anov.sdk.mgt.service.tenant;

import cn.woyun.anov.sdk.mgt.entity.SysTenantRoleRelation;
import cn.woyun.anov.sdk.mgt.mapper.SysTenantRoleRealtionMapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 租户与角色关系的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysTenantRoleRelationServiceImpl extends ServiceImpl<SysTenantRoleRealtionMapper, SysTenantRoleRelation> implements SysTenantRoleRelationService {

    /**
     * 保存租户下的角色。
     *
     * @param tenantId 租户主键。
     * @param roleIds  角色主键集合。
     */
    @Override
    @Transactional
    public void saveTenantToRole(final long tenantId, final Collection<Long> roleIds) {
        super.remove(
                createUpdateWrapper().lambda().eq(SysTenantRoleRelation::getTenantId, tenantId)
        );
        final List<SysTenantRoleRelation> sysTenantRoleRelations = new ArrayList<>(roleIds.size());
        for (long roleId : roleIds) {
            final SysTenantRoleRelation sysTenantRoleRelation = new SysTenantRoleRelation();
            sysTenantRoleRelation.setTenantId(tenantId);
            sysTenantRoleRelation.setRoleId(roleId);
            sysTenantRoleRelations.add(sysTenantRoleRelation);
        }
        super.saveBatch(sysTenantRoleRelations);
    }

    /**
     * @return 创建UpdateWrapper对象。
     */
    public UpdateWrapper<SysTenantRoleRelation> createUpdateWrapper() {
        return new UpdateWrapper<>();
    }

}