package cn.woyun.anov.sdk.dev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 大屏客户端
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DevScreenClient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户主键
     */
    private Long tenantId;

    /**
     * 大屏标识
     */
    private String screenKey;

    /**
     * 大屏等级。
     */
    private String screenLevel;

    /**
     * 大屏环境。
     */
    private String screenEnv;

    /**
     * 大屏工作目录。
     */
    private String screenDir;

    /**
     * 大屏框架版本
     */
    private String screenFrameworkVersion;

    /**
     * 大屏芯片版本
     */
    private String screenCoreVersion;

    /**
     * 大屏是否有子客户端
     */
    private Boolean screenHasChildren;

    /**
     * 大屏是否展开
     */
    private Boolean screenLeafed;

    /**
     * 客户端唯一标识
     */
    private String clientCode;

    /**
     * 客户端的远程终端标识
     */
    private String clientRemoteCode;

    /**
     * 是否在线。
     */
    private Boolean clientOnlined;

    /**
     * 上线时间
     */
    private LocalDateTime clientOnlineTime;

    /**
     * 下线时间
     */
    private LocalDateTime clientOfflineTime;

    /**
     * 客户端类型 PC或者APP等
     */
    private String clientType;

    /**
     * 客户端标题
     */
    private String clientTitle;

    /**
     * 客户端语言
     */
    private String clientLanguage;

    /**
     * 客户端分辨率
     */
    private String clientResoliving;

    /**
     * 客户端请求IP
     */
    private String clientRequestIp;

    /**
     * 客户端请求IP归属地
     */
    private String clientRequestIpInfo;

    /**
     * 客户端请求的页面地址
     */
    private String clientRequestUrl;

    /**
     * 客户端主机名称
     */
    private String clientDomain;

    /**
     * 客户端来源
     */
    private String clientReferer;

    /**
     * 客户端浏览器名称
     */
    private String clientBrowserName;

    /**
     * 客户端浏览器版本
     */
    private String clientBrowserVersion;

    /**
     * 客户端信息
     */
    private String clientAgent;

    /**
     * 客户端操作系统
     */
    private String clientOsName;

    /**
     * 客户端操作系统详情
     */
    private String clientOsInfo;

    /**
     * 客户端操作系统厂商
     */
    private String clientOsFamily;

    /**
     * 客户端显卡名称
     */
    private String clientGpuName;

    /**
     * 客户端显卡厂商
     */
    private String clientGpuFamily;

    /**
     * 客户端FPS
     */
    private String clientFps;

    /**
     * 客户端色彩
     */
    private String clientColour;

    /**
     * 客户端总内存
     */
    private String clientMemTotal;

    /**
     * 客户端可用内存
     */
    private String clientMemUsed;

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
