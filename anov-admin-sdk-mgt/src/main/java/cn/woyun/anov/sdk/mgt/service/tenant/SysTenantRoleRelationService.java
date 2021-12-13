package cn.woyun.anov.sdk.mgt.service.tenant;

import cn.woyun.anov.sdk.mgt.entity.SysTenantRoleRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * <p>
 * 租户与角色关系的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysTenantRoleRelationService extends IService<SysTenantRoleRelation> {

    /**
     * 保存租户下的角色。
     *
     * @param tenantId 租户主键。
     * @param roleIds  角色主键集合。
     */
    void saveTenantToRole(final long tenantId, final Collection<Long> roleIds);

}