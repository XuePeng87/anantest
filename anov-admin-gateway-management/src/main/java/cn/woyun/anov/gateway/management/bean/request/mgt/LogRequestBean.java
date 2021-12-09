package cn.woyun.anov.gateway.management.bean.request.mgt;

import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 操作日志的请求参数。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class LogRequestBean extends BaseRequestBean {

    private static final long serialVersionUID = 1L;

    /**
     * 租户主键。
     */
    private Long tenantId;

    /**
     * 系统。
     */
    private String logSystem;

    /**
     * 模块。
     */
    private String logModule;

    /**
     * 描述。
     */
    private String logDescription;

    /**
     * 类型。
     */
    private String logType;

    /**
     * 执行IP。
     */
    private String logIp;

    /**
     * 请求地址。
     */
    private String logReqUrl;

    /**
     * 请求浏览器。
     */
    private String logReqBrowser;

    /**
     * 请求操作系统。
     */
    private String logReqOs;

    /**
     * 请求处理类。
     */
    private String logClassName;

    /**
     * 请求处理方法。
     */
    private String logMethodName;

    /**
     * 请求参数。
     */
    private String logReqBody;

    /**
     * 创建人。
     */
    private String createUser;

    /**
     * 操作时间。
     */
    private LocalDateTime createTime;

}
