package cn.woyun.anov.gateway.management.bean.request.dev;

import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import cn.woyun.anov.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 大屏的请求参数类。
 *
 * @author xuepeng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "大屏的查询请求参数类")
public class DevScreenQueryRequestBean extends BaseRequestBean {

    /**
     * 名称
     */
    @ApiModelProperty(value = "大屏名称", required = true, position = 1)
    @Length(max = 128, message = "大屏名称长度不能大于128个字符")
    private String screenName;

    /**
     * 状态 0=停用；1=启用；
     */
    @ApiModelProperty(value = "大屏状态", position = 2)
    private Integer screenStatus;

    /**
     * 分页信息。
     */
    @ApiModelProperty(value = "分页", required = true, position = 3)
    private PageParam page;

}
