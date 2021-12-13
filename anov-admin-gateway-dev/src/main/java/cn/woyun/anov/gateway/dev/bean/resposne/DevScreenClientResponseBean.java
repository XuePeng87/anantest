package cn.woyun.anov.gateway.dev.bean.resposne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 大屏客户端响应数据类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode
@ToString
@ApiModel(value = "大屏客户端的响应类")
public class DevScreenClientResponseBean {

    /**
     * 主键。
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Integer id;

    /**
     * 大屏标识
     */
    @ApiModelProperty(value = "大屏标识", position = 2)
    private String screenKey;

    /**
     * 客户端标题
     */
    @ApiModelProperty(value = "客户端标题", position = 3)
    private String clientTitle;

    /**
     * 客户端唯一标识
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 4)
    private String clientCode;

    /**
     * 上线时间
     */
    @ApiModelProperty(value = "客户端上线时间", position = 5)
    private LocalDateTime clientOnlineTime;

}
