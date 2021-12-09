package cn.woyun.anov.gateway.management.bean.response.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色的响应类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "角色的响应数据类")
public class RoleResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    /**
     * 编号。
     */
    @ApiModelProperty(value = "编号", position = 2)
    private String roleCode;

    /**
     * 名称。
     */
    @ApiModelProperty(value = "名称", position = 3)
    private String roleName;

    /**
     * 角色级别 0=超级管理员；1=系统管理员；2=租户管理员；3=租户操作员
     */
    @ApiModelProperty(value = "级别", position = 4)
    private Integer roleLevel;

    /**
     * 备注。
     */
    @ApiModelProperty(value = "备注", position = 5)
    private String roleRemark;

    /**
     * 排序。
     */
    @ApiModelProperty(value = "排序", position = 6)
    private Integer roleSort;

    /**
     * 备注。
     */
    @ApiModelProperty(value = "创建时间", position = 7)
    private LocalDateTime createTime;

}