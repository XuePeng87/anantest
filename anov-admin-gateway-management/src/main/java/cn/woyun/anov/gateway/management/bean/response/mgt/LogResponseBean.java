package cn.woyun.anov.gateway.management.bean.response.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 日志的响应数据类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "角色的响应数据类")
public class LogResponseBean {

    /**
     * 系统。
     */
    @ApiModelProperty(value = "系统", position = 1)
    private String logSystem;

    /**
     * 模块。
     */
    @ApiModelProperty(value = "模块", position = 2)
    private String logModule;

    /**
     * 描述。
     */
    @ApiModelProperty(value = "描述", position = 3)
    private String logDescription;

    /**
     * 类型。
     */
    @ApiModelProperty(value = "类型", position = 4)
    private String logType;

    /**
     * 执行IP。
     */
    @ApiModelProperty(value = "执行IP", position = 5)
    private String logIp;

    /**
     * 请求地址。
     */
    @ApiModelProperty(value = "请求地址", position = 6)
    private String logReqUrl;

    /**
     * 请求浏览器。
     */
    @ApiModelProperty(value = "请求浏览器", position = 7)
    private String logReqBrowser;

    /**
     * 请求操作系统。
     */
    @ApiModelProperty(value = "请求操作系统", position = 8)
    private String logReqOs;

    /**
     * 请求处理类。
     */
    @ApiModelProperty(value = "请求处理类", position = 9)
    private String logClassName;

    /**
     * 请求处理方法。
     */
    @ApiModelProperty(value = "请求处理方法", position = 10)
    private String logMethodName;

    /**
     * 请求参数。
     */
    @ApiModelProperty(value = "请求参数", position = 11)
    private String logReqBody;

    /**
     * 创建人。
     */
    @ApiModelProperty(value = "创建人", position = 12)
    private String createUser;

    /**
     * 操作时间。
     */
    @ApiModelProperty(value = "操作时间", position = 13)
    private LocalDateTime createTime;

}
