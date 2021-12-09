package cn.woyun.anov.gateway.management.bean.request.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 手机控制器登录的请求参数类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "用户登录的请求参数类")
public class UserLoginRequestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "验证码编号", required = true, position = 1)
    private String uuid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "验证码", required = true, position = 2)
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "用户名", required = true, position = 3)
    private String username;

    /**
     * 名称
     */
    @ApiModelProperty(value = "密码", required = true, position = 4)
    private String password;

}
