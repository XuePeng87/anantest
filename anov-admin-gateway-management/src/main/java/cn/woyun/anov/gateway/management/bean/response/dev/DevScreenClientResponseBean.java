package cn.woyun.anov.gateway.management.bean.response.dev;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 大屏客户端响应数据类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "大屏客户端响应数据类")
public class DevScreenClientResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    /**
     * 租户主键
     */
    @ApiModelProperty(value = "租户主键", position = 2)
    private Long tenantId;

    /**
     * 大屏标识
     */
    @ApiModelProperty(value = "大屏标识", position = 3)
    private String screenKey;

    /**
     * 大屏等级。
     */
    @ApiModelProperty(value = "大屏等级", position = 4)
    private String screenLevel;

    /**
     * 大屏环境。
     */
    @ApiModelProperty(value = "大屏环境", position = 5)
    private String screenEnv;

    /**
     * 大屏工作目录。
     */
    @ApiModelProperty(value = "大屏工作目录", position = 6)
    private String screenDir;

    /**
     * 大屏框架版本
     */
    @ApiModelProperty(value = "大屏框架版本", position = 7)
    private String screenFrameworkVersion;

    /**
     * 大屏芯片版本
     */
    @ApiModelProperty(value = "大屏芯片版本", position = 8)
    private String screenCoreVersion;

    /**
     * 大屏是否有子客户端
     */
    @ApiModelProperty(value = "大屏是否有子客户端", position = 9)
    private Boolean screenHasChildren;

    /**
     * 大屏是否展开
     */
    @ApiModelProperty(value = "大屏是否展开", position = 10)
    private Boolean screenLeafed;

    /**
     * 客户端唯一标识
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 11)
    private String clientCode;

    /**
     * 客户端的远程终端标识
     */
    @ApiModelProperty(value = "客户端的远程终端标识", position = 12)
    private String clientRemoteCode;

    /**
     * 是否在线。
     */
    @ApiModelProperty(value = "是否在线", position = 13)
    private Boolean clientOnlined;

    /**
     * 上线时间
     */
    @ApiModelProperty(value = "上线时间", position = 14)
    private LocalDateTime clientOnlineTime;

    /**
     * 下线时间
     */
    @ApiModelProperty(value = "下线时间", position = 15)
    private LocalDateTime clientOfflineTime;

    /**
     * 客户端类型 PC或者APP等
     */
    @ApiModelProperty(value = "客户端类型 PC或者APP等", position = 16)
    private String clientType;

    /**
     * 客户端标题
     */
    @ApiModelProperty(value = "客户端标题", position = 17)
    private String clientTitle;

    /**
     * 客户端语言
     */
    @ApiModelProperty(value = "客户端语言", position = 18)
    private String clientLanguage;

    /**
     * 客户端分辨率
     */
    @ApiModelProperty(value = "客户端分辨率", position = 19)
    private String clientResoliving;

    /**
     * 客户端请求IP
     */
    @ApiModelProperty(value = "客户端请求IP", position = 20)
    private String clientRequestIp;

    /**
     * 客户端请求IP归属地
     */
    @ApiModelProperty(value = "客户端请求IP归属地", position = 21)
    private String clientRequestIpInfo;

    /**
     * 客户端请求的页面地址
     */
    @ApiModelProperty(value = "客户端请求的页面地址", position = 22)
    private String clientRequestUrl;

    /**
     * 客户端主机名称
     */
    @ApiModelProperty(value = "客户端主机名称", position = 23)
    private String clientDomain;

    /**
     * 客户端来源
     */
    @ApiModelProperty(value = "客户端来源", position = 24)
    private String clientReferer;

    /**
     * 客户端浏览器名称
     */
    @ApiModelProperty(value = "客户端浏览器名称", position = 25)
    private String clientBrowserName;

    /**
     * 客户端浏览器版本
     */
    @ApiModelProperty(value = "客户端浏览器版本", position = 26)
    private String clientBrowserVersion;

    /**
     * 客户端信息
     */
    @ApiModelProperty(value = "客户端信息", position = 27)
    private String clientAgent;

    /**
     * 客户端操作系统
     */
    @ApiModelProperty(value = "客户端操作系统", position = 28)
    private String clientOsName;

    /**
     * 客户端操作系统详情
     */
    @ApiModelProperty(value = "客户端操作系统详情", position = 29)
    private String clientOsInfo;

    /**
     * 客户端操作系统厂商
     */
    @ApiModelProperty(value = "客户端操作系统厂商", position = 30)
    private String clientOsFamily;

    /**
     * 客户端显卡名称
     */
    @ApiModelProperty(value = "客户端显卡名称", position = 31)
    private String clientGpuName;

    /**
     * 客户端显卡厂商
     */
    @ApiModelProperty(value = "客户端显卡厂商", position = 32)
    private String clientGpuFamily;

    /**
     * 客户端FPS
     */
    @ApiModelProperty(value = "客户端FPS", position = 33)
    private String clientFps;

    /**
     * 客户端色彩
     */
    @ApiModelProperty(value = "客户端色彩", position = 34)
    private String clientColour;

    /**
     * 客户端总内存
     */
    @ApiModelProperty(value = "客户端总内存", position = 35)
    private String clientMemTotal;

    /**
     * 客户端可用内存
     */
    @ApiModelProperty(value = "客户端可用内存", position = 36)
    private String clientMemUsed;

}
