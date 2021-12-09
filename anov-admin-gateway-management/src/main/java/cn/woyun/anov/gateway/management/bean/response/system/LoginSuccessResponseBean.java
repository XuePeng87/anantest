package cn.woyun.anov.gateway.management.bean.response.system;

import cn.woyun.anov.sdk.mgt.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 登录成功的响应数据类。
 *
 * @author xuepeng
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "登录成功的响应数据类")
public class LoginSuccessResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 令牌Header名称。
     */
    @ApiModelProperty(value = "令牌名称", position = 1)
    private String tokenName;

    /**
     * 令牌Hedaer内容。
     */
    @ApiModelProperty(value = "令牌内容", position = 2)
    private String tokenValue;

    /**
     * 登录成功的账号。
     */
    @ApiModelProperty(value = "当前登录用户", position = 3)
    private SysUser sysUser;

}
