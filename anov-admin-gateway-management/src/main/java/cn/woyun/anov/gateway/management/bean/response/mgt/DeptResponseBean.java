package cn.woyun.anov.gateway.management.bean.response.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门的响应数据类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "部门的响应数据类")
public class DeptResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    /**
     * 上级部门
     */
    @ApiModelProperty(value = "父级主键", position = 2)
    private Long pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 3)
    private String deptName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", position = 4)
    private Integer deptSort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 5)
    private LocalDateTime createTime;

    /**
     * 子部门。
     */
    @ApiModelProperty(value = "子部门", position = 6)
    private List<DeptResponseBean> children = new ArrayList<>();

}
