package cn.woyun.anov.gateway.dev.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 手机控制器登录的请求参数类。
 *
 * @author xuepeng
 */
@Data
@ApiModel(value = "手机控制器登录的请求参数类")
public class AppLoginRequestBean {

    /**
     * 名称
     */
    @ApiModelProperty(value = "验证码编号", required = true, position = 1)
    @NotEmpty(message = "验证码编号不能为空")
    @Length(max = 32, message = "验证码编号长度不能大于32个字符")
    private String uuid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "验证码", required = true, position = 2)
    @NotEmpty(message = "验证码不能为空")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "用户名", required = true, position = 3)
    @NotEmpty(message = "用户名不能为空")
    @Length(max = 32, message = "用户名长度不能大于32个字符")
    private String username;

    /**
     * 名称
     */
    @ApiModelProperty(value = "密码", required = true, position = 4)
    @NotEmpty(message = "密码不能为空")
    private String password;

}
