package cn.woyun.anov.gateway.management.bean.request.dev;

import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import cn.woyun.anov.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 大屏的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "大屏客户端的查询请求参数类")
public class DevScreenClientQueryRequestBean extends BaseRequestBean {

    /**
     * 大屏标识
     */
    @ApiModelProperty(value = "大屏标识", position = 1)
    private String screenKey;

    /**
     * 客户端类型 PC或者APP等
     */
    @ApiModelProperty(value = "客户端类型 PC或者APP等", position = 2)
    private String clientType;

    /**
     * 客户端唯一标识
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 3)
    private String clientCode;

    /**
     * 远程控制的客户端唯一标识
     */
    @ApiModelProperty(value = "远程控制的客户端唯一标识", position = 4)
    private String clientRemoteCode;

    /**
     * 开始时间。
     */
    @ApiModelProperty(value = "开始时间", position = 5)
    private LocalDateTime beginTime;

    /**
     * 结束时间。
     */
    @ApiModelProperty(value = "结束时间", position = 6)
    private LocalDateTime endTime;

    /**
     * 分页信息。
     */
    @ApiModelProperty(value = "分页", required = true, position = 7)
    private PageParam page;

}
