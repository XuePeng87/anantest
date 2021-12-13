package cn.woyun.anov.gateway.management.bean.request.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 登录的请求参数类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "登录的请求参数类")
public class UserLoginRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 验证码编号
     */
    @ApiModelProperty(value = "验证码编号", required = true, position = 1)
    @NotEmpty(message = "验证码编号不能为空")
    @Length(max = 32, message = "验证码编号长度不能大于32个字符")
    private String uuid;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true, position = 2)
    @NotEmpty(message = "验证码不能为空")
    private String code;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true, position = 3)
    @NotEmpty(message = "用户名不能为空")
    @Length(max = 32, message = "用户名长度不能大于32个字符")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true, position = 4)
    @NotEmpty(message = "密码不能为空")
    private String password;

}
