package cn.woyun.anov.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 租户角色的关系表
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysTenantRoleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户主键
     */
    private Long tenantId;

    /**
     * 角色主键
     */
    private Long roleId;


}
