package cn.woyun.anov.gateway.management.bean.response.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "租户的响应数据类")
public class TenantResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 2)
    private String tenantName;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机", position = 3)
    private String tenantPhone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", position = 4)
    private String tenantEmail;

    /**
     * 等级 0=测试；1=普通；2=高级。
     */
    @ApiModelProperty(value = "等级 0=测试；1=普通；2=高级。", position = 5)
    private Integer tenantLevel;

    /**
     * 状态 0=停用；1=启用
     */
    @ApiModelProperty(value = "状态 0=停用；1=启用。", position = 6)
    private Integer tenantStatus;

    /**
     * 来源 0=自主，1=系统
     */
    @ApiModelProperty(value = "来源 0=自主，1=系统。", position = 7)
    private Integer tenantOrigin;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间。", position = 8)
    private LocalDateTime createTime;

    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期", position = 9)
    private LocalDateTime tenantExpireTime;

}
