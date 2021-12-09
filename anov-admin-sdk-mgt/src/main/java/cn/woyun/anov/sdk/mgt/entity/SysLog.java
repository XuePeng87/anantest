package cn.woyun.anov.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户主键
     */
    private String tenantId;

    /**
     * 操作系统
     */
    private String logSystem;

    /**
     * 操作模块
     */
    private String logModule;

    /**
     * 操作描述
     */
    private String logDescription;

    /**
     * 操作类型
     */
    private String logType;

    /**
     * 执行IP
     */
    private String logIp;

    /**
     * 请求地址
     */
    private String logReqUrl;

    /**
     * 请求浏览器
     */
    private String logReqBrowser;

    /**
     * 请求操作系统
     */
    private String logReqOs;

    /**
     * 请求处理类
     */
    private String logClassName;

    /**
     * 请求处理方法
     */
    private String logMethodName;

    /**
     * 请求参数
     */
    private String logReqBody;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 开始时间。
     */
    @TableField(exist = false)
    private LocalDateTime beginTime;

    /**
     * 结束时间。
     */
    @TableField(exist = false)
    private LocalDateTime endTime;

}
