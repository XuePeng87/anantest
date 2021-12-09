package cn.woyun.anov.gateway.management.bean.request.mgt;

import cn.woyun.anov.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

/**
 * 租户的查询请求参数类。
 *
 * @author xuepeng
 */
@Data
@ToString
@ApiModel(value = "租户的查询请求参数类")
public class TenantQueryRequestBean {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 1)
    @Length(max = 128, message = "租户名称长度不能大于128个字符")
    private String tenantName;

    /**
     * 手机
     */
    @ApiModelProperty(value = "手机", position = 2)
    @Length(max = 32, message = "租户手机号长度不能大于32个字符")
    private String tenantPhone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", position = 3)
    @Length(max = 128, message = "租户邮箱长度不能大于128个字符")
    @Email(message = "租户邮箱格式不正确")
    private String tenantEmail;

    /**
     * 级别 0=试用；1=正式；2=系统。
     */
    @ApiModelProperty(value = "级别 0=试用；1=正式；2=系统", position = 4)
    private Integer tenantLevel;

    /**
     * 状态 0=停用；1=启用
     */
    @ApiModelProperty(value = "状态 0=停用；1=启用", position = 5)
    private Integer tenantStatus;

    /**
     * 来源 0=自主，1=系统
     */
    @ApiModelProperty(value = "来源 0=自主，1=系统", position = 6)
    private Integer tenantOrigin;

    /**
     * 开始时间。
     */
    @ApiModelProperty(value = "开始时间", position = 7)
    private LocalDateTime beginTime;

    /**
     * 结束时间。
     */
    @ApiModelProperty(value = "结束时间", position = 8)
    private LocalDateTime endTime;

    /**
     * 分页信息。
     */
    @ApiModelProperty(value = "分页", required = true, position = 9)
    private PageParam page;

}
