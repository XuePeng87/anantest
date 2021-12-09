package cn.woyun.anov.gateway.management.bean.request.dev;

import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 大屏客户端强制下线的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "大屏客户端强制下线的请求参数类")
public class DevScreenClientQuitRequestBean extends BaseRequestBean {

    /**
     * 大屏标识
     */
    @ApiModelProperty(value = "大屏标识", position = 1)
    private String screenKey;

    /**
     * 客户端唯一标识
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 2)
    private String clientCode;

}
