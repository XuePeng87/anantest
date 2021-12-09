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
 * 部门的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "部门的请求参数类")
public class DeptRequestBean extends BaseRequestBean {

    /**
     * 上级部门
     */
    @ApiModelProperty(value = "父级部门主键", notes = "0=根部门", required = true, position = 1)
    @NotNull(message = "父级部门主键不能为空")
    private Long pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "部门名称", required = true, position = 2)
    @NotBlank(message = "部门名称不能为空")
    @Length(max = 32, message = "部门名称长度不能大于32个字符")
    private String deptName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "部门排序", required = true, position = 3)
    @NotNull(message = "部门排序不能为空")
    private Integer deptSort;

    /**
     * 状态  0=停用；1=启用
     */
    @ApiModelProperty(value = "部门状态", notes = "0=停用；1=启用", required = true, position = 4)
    @NotNull(message = "部门状态不能为空")
    private Integer deptStatus;

}
