package cn.woyun.anov.gateway.dev.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
