package cn.woyun.anov.gateway.management.bean.request.dev;

import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import cn.woyun.anov.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "大屏客户端的查询请求参数类")
public class DevScreenLogQueryRequestBean extends BaseRequestBean {

    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型", position = 1)
    private String logType;

    /**
     * 日志模块
     */
    @ApiModelProperty(value = "日志模块", position = 2)
    private String logModule;

    /**
     * 日志描述
     */
    @ApiModelProperty(value = "日志描述", position = 3)
    private String logDesc;

    /**
     * 大屏标识
     */
    @ApiModelProperty(value = "大屏标识", position = 4)
    private String screenKey;

    /**
     * 客户端唯一标识
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 5)
    private String clientCode;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", position = 6)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", position = 7)
    private LocalDateTime endTime;

    /**
     * 分页信息
     */
    @ApiModelProperty(value = "分页信息", required = true, position = 8)
    private PageParam page;

}
