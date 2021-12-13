package cn.woyun.anov.gateway.dev.bean.resposne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@ApiModel(value = "客户端登录的返回值")
public class DevAppLoginResponseBean {

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
     * 客户端唯一标识。
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 3)
    private String clientCode;

}
