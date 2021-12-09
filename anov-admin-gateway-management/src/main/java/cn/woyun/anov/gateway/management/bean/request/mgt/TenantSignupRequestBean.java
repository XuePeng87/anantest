package cn.woyun.anov.gateway.management.bean.request.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 租户的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "租户的请求参数类")
public class TenantSignupRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称。
     */
    @ApiModelProperty(value = "租户名称", required = true, position = 1)
    @NotBlank(message = "租户名称不能为空")
    @Length(max = 128, message = "租户名称长度不能大于128个字符")
    private String tenantName;

    /**
     * 手机
     */
    @ApiModelProperty(value = "租户手机号", required = true, position = 2)
    @NotBlank(message = "租户电话号不能为空")
    @Length(max = 32, message = "租户手机号长度不能大于32个字符")
    private String tenantPhone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "租户邮箱", required = true, position = 3)
    @NotBlank(message = "租户邮箱不能为空")
    @Length(max = 128, message = "租户邮箱长度不能大于128个字符")
    @Email(message = "租户邮箱格式不正确")
    private String tenantEmail;

    /**
     * 租户来源
     */
    @ApiModelProperty(value = "租户来源", required = true, position = 4)
    @NotNull(message = "租户来源不能为空")
    private Integer tenantOrigin;

    /**
     * 管理帐号。
     */
    @ApiModelProperty(value = "租户的管理帐号", required = true, position = 5)
    @NotBlank(message = "租户管理帐号不能为空")
    @Length(max = 32, message = "租户管理帐号长度不能大于32个字符")
    private String userAccount;

}