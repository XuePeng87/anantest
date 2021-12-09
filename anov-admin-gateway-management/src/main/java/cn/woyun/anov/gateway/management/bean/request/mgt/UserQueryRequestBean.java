package cn.woyun.anov.gateway.management.bean.request.mgt;

import cn.woyun.anov.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 用户的查询请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户的查询请求参数类")
public class UserQueryRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帐号。
     */
    @ApiModelProperty(value = "用户所属部门", position = 1)
    private Long deptId;

    /**
     * 帐号。
     */
    @ApiModelProperty(value = "用户帐号", required = true, position = 2)
    @Length(max = 32, message = "用户帐号长度不能大于32个字符")
    private String userAccount;

    /**
     * 姓名。
     */
    @ApiModelProperty(value = "用户姓名", required = true, position = 3)
    @Length(max = 32, message = "用户姓名长度不能大于32个字符")
    private String userName;

    /**
     * 电话号。
     */
    @ApiModelProperty(value = "用户电话号", required = true, position = 4)
    @Length(max = 32, message = "用户电话号长度不能大于32个字符")
    private String userPhone;

    /**
     * 邮箱。
     */
    @ApiModelProperty(value = "用户邮箱", required = true, position = 5)
    @Length(max = 128, message = "用户邮箱长度不能大于32个字符")
    private String userEmail;

    /**
     * 状态 0=停用，1=启用。
     */
    @ApiModelProperty(value = "用户状态", notes = "0=停用；1=启用", required = true, position = 6)
    private Integer userStatus;

    /**
     * 分页信息。
     */
    @ApiModelProperty(value = "分页", required = true, position = 7)
    private PageParam page;

}
