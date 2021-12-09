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
 * 功能的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "功能的请求参数类")
public class FuncRequestBean extends BaseRequestBean {

    /**
     * 上级功能项
     */
    @ApiModelProperty(value = "父级功能主键", notes = "0=目录", required = true, position = 1)
    @NotNull(message = "父级功能主键不能为空")
    private Long pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "功能名称", required = true, position = 2)
    @NotBlank(message = "功能名称不能为空")
    @Length(max = 32, message = "部门名称长度不能大于32个字符")
    private String funcName;

    /**
     * 编号
     */
    @ApiModelProperty(value = "功能编号", position = 3)
    @Length(max = 32, message = "功能编号长度不能大于32个字符")
    private String funcCode;

    /**
     * 类型 0=目录，1=菜单；2=按钮
     */
    @ApiModelProperty(value = "功能类型", required = true, position = 4)
    @NotNull(message = "功能类型不能为空")
    private Integer funcType;

    /**
     * 图标
     */
    @ApiModelProperty(value = "功能图标", position = 5)
    @Length(max = 32, message = "功能图标长度不能大于32个字符")
    private String funcIcon;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "功能路由地址", position = 6)
    @Length(max = 128, message = "功能路由长度不能大于128个字符")
    private String funcPath;

    /**
     * 是否外链
     */
    @ApiModelProperty(value = "功能是否外链", position = 7)
    private Boolean funcLinkable;

    /**
     * 是否缓存
     */
    @ApiModelProperty(value = "功能是否缓存", position = 8)
    private Boolean funcCacheable;

    /**
     * 是否可见
     */
    @ApiModelProperty(value = "功能是否可见", position = 9)
    private Boolean funcVisible;

    /**
     * 组件名称
     */
    @ApiModelProperty(value = "功能组件名称", position = 10)
    @Length(max = 32, message = "功能组件名称长度不能大于32个字符")
    private String funcComponentName;

    /**
     * 组件地址
     */
    @ApiModelProperty(value = "功能组件地址", position = 11)
    @Length(max = 128, message = "功能组件地址长度不能大于128个字符")
    private String funcComponentPath;

    /**
     * 排序
     */
    @ApiModelProperty(value = "功能排序", required = true, position = 12)
    @NotNull(message = "功能排序不能为空")
    private Integer funcSort;

}
