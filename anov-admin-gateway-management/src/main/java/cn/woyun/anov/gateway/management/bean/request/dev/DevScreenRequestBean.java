package cn.woyun.anov.gateway.management.bean.request.dev;

import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 大屏的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "大屏的请求参数类")
public class DevScreenRequestBean extends BaseRequestBean {

    /**
     * 名称
     */
    @ApiModelProperty(value = "大屏唯一标识", required = true, position = 1)
    @Length(max = 12, message = "大屏唯一标识长度不能大于12个字符")
    private String screenKey;

    /**
     * 名称
     */
    @ApiModelProperty(value = "大屏名称", required = true, position = 2)
    @NotBlank(message = "大屏名称不能为空")
    @Length(max = 128, message = "大屏名称长度不能大于128个字符")
    private String screenName;

    /**
     * 有效期
     */
    @ApiModelProperty(value = "大屏有效期", position = 3)
    private LocalDateTime screenExpireTime;

    /**
     * 状态 0=停用；1=启用；
     */
    @ApiModelProperty(value = "大屏状态", position = 4)
    private Integer screenStatus;

    /**
     * 是否需要认证
     */
    @ApiModelProperty(value = "大屏是否需要认证", position = 5)
    private Boolean screenAuthenticated;

    /**
     * 备注
     */
    @ApiModelProperty(value = "大屏备注", position = 6)
    @Length(max = 256, message = "大屏名称长度不能大于256个字符")
    private String screenRemark;

    /**
     * 大屏功能
     */
    @ApiModelProperty(value = "大屏功能", position = 7)
    private List<String> funcs = new ArrayList<>();

}
