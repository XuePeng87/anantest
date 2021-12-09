package cn.woyun.anov.gateway.management.bean.request.mgt;

import cn.woyun.anov.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 日志的查询请求参数。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "日志的查询请求参数类")
public class LogQueryRequestBean {

    private static final long serialVersionUID = 1L;

    /**
     * 系统。
     */
    @ApiModelProperty(value = "操作系统", position = 1)
    @Length(max = 32, message = "操作系统长度不能大于32个字符")
    private String logSystem;

    /**
     * 模块。
     */
    @ApiModelProperty(value = "操作模块", position = 2)
    @Length(max = 32, message = "操作模块长度不能大于32个字符")
    private String logModule;

    /**
     * 类型。
     */
    @ApiModelProperty(value = "日志类型", position = 3)
    @Length(max = 32, message = "日志类型长度不能大于32个字符")
    private String logType;

    /**
     * 执行IP。
     */
    @ApiModelProperty(value = "日志IP", position = 4)
    @Length(max = 32, message = "日志IP长度不能大于32个字符")
    private String logIp;

    /**
     * 请求地址。
     */
    @ApiModelProperty(value = "日志请求地址", position = 5)
    @Length(max = 512, message = "日志请求地址长度不能大于512个字符")
    private String logReqUrl;

    /**
     * 请求浏览器。
     */
    @ApiModelProperty(value = "日志请求浏览器", position = 6)
    @Length(max = 128, message = "日志请求浏览器长度不能大于128个字符")
    private String logReqBrowser;

    /**
     * 请求处理类。
     */
    @ApiModelProperty(value = "日志请求处理类", position = 7)
    @Length(max = 512, message = "日志请求处理类长度不能大于512个字符")
    private String logClassName;

    /**
     * 请求处理方法。
     */
    @ApiModelProperty(value = "日志请求处理方法", position = 8)
    @Length(max = 128, message = "日志请求处理方法长度不能大于128个字符")
    private String logMethodName;

    /**
     * 创建人。
     */
    @ApiModelProperty(value = "创建人", position = 9)
    @Length(max = 32, message = "创建人长度不能大于32个字符")
    private String createUser;

    /**
     * 开始时间。
     */
    @ApiModelProperty(value = "开始时间", position = 10)
    private LocalDateTime beignTime;

    /**
     * 结束时间。
     */
    @ApiModelProperty(value = "结束时间", position = 11)
    private LocalDateTime endTime;

    /**
     * 分页信息。
     */
    @ApiModelProperty(value = "分页", required = true, position = 12)
    private PageParam page;

}
