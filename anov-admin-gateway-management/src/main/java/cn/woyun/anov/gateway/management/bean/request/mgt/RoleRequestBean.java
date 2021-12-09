package cn.woyun.anov.gateway.management.bean.request.mgt;

import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色的请求参数类")
public class RoleRequestBean extends BaseRequestBean {

    /**
     * 名称
     */
    @ApiModelProperty(value = "角色名称", required = true, position = 1)
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 32, message = "角色名称长度不能大于32个字符")
    private String roleName;

    /**
     * 编号
     */
    @ApiModelProperty(value = "角色编号", required = true, position = 2)
    @NotBlank(message = "角色编号不能为空")
    @Length(max = 32, message = "角色编号长度不能大于32个字符")
    private String roleCode;

    /**
     * 角色级别 0=超级管理员；1=系统管理员；2=租户管理员；3=租户操作员
     */
    @ApiModelProperty(value = "角色级别", required = true, position = 3)
    @NotNull(message = "角色级别不能为空")
    private Integer roleLevel;

    /**
     * 排序
     */
    @ApiModelProperty(value = "角色排序", required = true, position = 4)
    @NotNull(message = "角色排序数据权限不能为空")
    private Integer roleSort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "角色备注", position = 5)
    private String roleRemark;

}
