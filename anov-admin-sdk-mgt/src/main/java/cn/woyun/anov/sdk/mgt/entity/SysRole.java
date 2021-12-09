package cn.woyun.anov.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统角色表 
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String roleName;

    /**
     * 编号
     */
    private String roleCode;

    /**
     * 角色级别 0=超级管理员；1=系统管理员；2=租户管理员；3=租户操作员
     */
    private Integer roleLevel;

    /**
     * 排序
     */
    private Integer roleSort;

    /**
     * 状态 0=停用；1=启用
     */
    private Integer roleStatus;

    /**
     * 备注
     */
    private String roleRemark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;


}
