package cn.woyun.anov.gateway.management.bean.request.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 部门的查询请求参数类。
 *
 * @author xuepeng
 */
@Data
@ToString
@ApiModel(value = "部门的查询请求参数类")
public class DeptQueryRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 上级部门
     */
    @ApiModelProperty(value = "父级部门主键", notes = "0=根部门", position = 1)
    private Long pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "部门名称", position = 2)
    @Length(max = 32, message = "部门名称长度不能大于32个字符")
    private String deptName;

    /**
     * 状态  0=停用；1=启用
     */
    @ApiModelProperty(value = "部门状态", notes = "0=停用；1=启用", position = 3)
    private Integer deptStatus;

}
