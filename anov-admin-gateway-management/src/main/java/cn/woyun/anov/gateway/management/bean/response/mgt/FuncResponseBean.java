package cn.woyun.anov.gateway.management.bean.response.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能的响应数据类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "功能的响应数据类")
public class FuncResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 上级功能项
     */
    @ApiModelProperty(value = "上级功能项")
    private Long pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String funcName;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String funcCode;

    /**
     * 类型 0=目录，1=菜单；2=按钮
     */
    @ApiModelProperty(value = "类型 0=目录，1=菜单；2=按钮")
    private Integer funcType;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String funcIcon;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String funcPath;

    /**
     * 是否外链
     */
    @ApiModelProperty(value = "是否外链")
    private Boolean funcLinkable;

    /**
     * 是否缓存
     */
    @ApiModelProperty(value = "是否缓存")
    private Boolean funcCacheable;

    /**
     * 是否可见
     */
    @ApiModelProperty(value = "是否可见")
    private Boolean funcVisible;

    /**
     * 组件名称
     */
    @ApiModelProperty(value = "组件名称")
    private String funcComponentName;

    /**
     * 组件地址
     */
    @ApiModelProperty(value = "组件地址")
    private String funcComponentPath;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer funcSort;

    /**
     * 子功能。
     */
    @ApiModelProperty(value = "子功能")
    private List<FuncResponseBean> children = new ArrayList<>();

}
