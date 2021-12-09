package cn.woyun.anov.gateway.management.bean.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * BMS网关的请求参数父类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseRequestBean implements Serializable {

    /**
     * 创建人。
     */
    @ApiModelProperty(hidden = true)
    private String createUser;
    /**
     * 修改人。
     */
    @ApiModelProperty(hidden = true)
    private String modifyUser;

}
