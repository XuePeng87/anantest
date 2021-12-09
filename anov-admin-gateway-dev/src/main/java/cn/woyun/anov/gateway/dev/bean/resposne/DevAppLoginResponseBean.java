package cn.woyun.anov.gateway.dev.bean.resposne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "客户端登录的返回值")
public class DevAppLoginResponseBean {

    /**
     * 访问令牌。
     */
    @ApiModelProperty(value = "访问令牌", position = 1)
    private String token;

    /**
     * 客户端唯一标识。
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 2)
    private String clientCode;

}
