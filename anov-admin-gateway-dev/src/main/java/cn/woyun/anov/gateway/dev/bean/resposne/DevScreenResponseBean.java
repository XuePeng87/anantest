package cn.woyun.anov.gateway.dev.bean.resposne;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@EqualsAndHashCode
@ToString
@ApiModel(value = "大屏的响应类")
public class DevScreenResponseBean {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 2)
    private String screenName;

    /**
     * 标识
     */
    @ApiModelProperty(value = "标识", position = 3)
    private String screenKey;

    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期", position = 4)
    private LocalDateTime screenExpireTime;

    /**
     * 是否需要认证
     */
    @ApiModelProperty(value = "是否需要认证", position = 5)
    private Boolean screenAuthenticated;

    /**
     * 芯片
     */
    @ApiModelProperty(value = "大屏芯片", position = 6)
    private String screenCore;

    /**
     * 功能
     */
    @ApiModelProperty(value = "功能", position = 7)
    private Collection<String> funcs = new ArrayList<>();

    /**
     * 客户端唯一标识。
     */
    @ApiModelProperty(value = "客户端唯一标识", position = 8)
    private String clientCode;

    /**
     * 客户端请求IP。
     */
    @ApiModelProperty(value = "客户端请求IP", position = 9)
    private String clientIp;

    /**
     * 客户端请求IP归属地。
     */
    @ApiModelProperty(value = "客户端请求IP归属地", position = 10)
    private String clientIpInfo;

    /**
     * 客户端请求IP归属地。
     */
    @ApiModelProperty(value = "客户端请求IP归属地", position = 10)
    private Boolean verify;

}
