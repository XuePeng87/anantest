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
 * 用户的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户的请求参数类")
public class UserRequestBean extends BaseRequestBean {

    private static final long serialVersionUID = 1L;

    /**
     * 帐号。
     */
    @ApiModelProperty(value = "用户所属部门", required = true, position = 1)
    @NotNull(message = "用户所属部门不能为空")
    private Long deptId;

    /**
     * 帐号。
     */
    @ApiModelProperty(value = "用户帐号", required = true, position = 2)
    @NotBlank(message = "用户帐号不能为空")
    @Length(max = 32, message = "用户帐号长度不能大于32个字符")
    private String userAccount;

    /**
     * 姓名。
     */
    @ApiModelProperty(value = "用户姓名", required = true, position = 3)
    @NotBlank(message = "用户姓名不能为空")
    @Length(max = 32, message = "用户姓名长度不能大于32个字符")
    private String userName;

    /**
     * 邮箱。
     */
    @ApiModelProperty(value = "用户邮箱", required = true, position = 4)
    @NotBlank(message = "用户邮箱不能为空")
    @Length(max = 128, message = "用户长度不能大于128个字符")
    private String userEmail;

    /**
     * 电话。
     */
    @ApiModelProperty(value = "用户手机号", required = true, position = 5)
    @NotBlank(message = "用户手机号不能为空")
    @Length(max = 32, message = "用户手机号长度不能大于32个字符")
    private String userPhone;

    /**
     * 状态 0=停用；1=启用。
     */
    @ApiModelProperty(value = "用户状态", notes = "0=停用；1=启用", required = true, position = 6)
    @NotNull(message = "用户状态不能为空")
    private Integer userStatus;

}