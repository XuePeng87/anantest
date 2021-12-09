package cn.woyun.anov.gateway.management.bean.request.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户的修改密码请求参数类。
 *
 * @author xuepeng
 */
@Data
@ApiModel(value = "用户的修改密码请求参数类")
public class UserPasswordRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 旧密码。
     */
    @ApiModelProperty(value = "旧密码", required = true, position = 1)
    @NotBlank(message = "旧密码不能为空")
    @Length(max = 32, message = "旧密码长度不能大于32个字符")
    private String oldPassword;

    /**
     * 新密码。
     */
    @ApiModelProperty(value = "新密码", required = true, position = 2)
    @NotBlank(message = "新密码不能为空")
    @Length(max = 32, message = "新密码长度不能大于32个字符")
    private String newPassword;

}
