package cn.woyun.anov.gateway.management.bean.response.dev;

import cn.woyun.anov.sdk.dev.entity.DevScreenPic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 大屏的响应数据类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "大屏的响应数据类")
public class DevScreenResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    /**
     * 部门主键
     */
    @ApiModelProperty(value = "部门主键", position = 2)
    private Long deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", position = 3)
    private String deptName;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 4)
    private String screenName;

    /**
     * 标识
     */
    @ApiModelProperty(value = "标识", position = 5)
    private String screenKey;

    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期", position = 6)
    private LocalDateTime screenExpireTime;

    /**
     * 监控的首张图片。
     */
    @ApiModelProperty(value = "监控的首张截图", position = 7)
    private String screenMonitorPic;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 8)
    private String screenRemark;

    /**
     * 状态 0=停用；1=启用；
     */
    @ApiModelProperty(value = "状态 0=停用；1=启用；", position = 9)
    private Integer screenStatus;

    /**
     * 是否需要认证
     */
    @ApiModelProperty(value = "是否需要认证", position = 10)
    private Boolean screenAuthenticated;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", position = 11)
    private String screenUrl;

    /**
     * 是否可预览
     */
    @ApiModelProperty(value = "是否可预览", position = 12)
    private Boolean screenPreviewed;

    /**
     * 验证次数
     */
    @ApiModelProperty(value = "验证次数", position = 13)
    private Integer screenCheckCount;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 14)
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 15)
    private LocalDateTime createTime;

    /**
     * 图片。
     */
    @ApiModelProperty(value = "首页图片", position = 16)
    private List<DevScreenPic> pics;

}
