package cn.woyun.anov.gateway.management.bean.request.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 功能的查询请求参数类。
 *
 * @author xuepeng
 */
@Data
@ToString
@ApiModel(value = "功能的查询请求参数类")
public class FuncQueryRequestBean {

    /**
     * 上级功能
     */
    @ApiModelProperty(value = "父级功能主键", notes = "0=目录", position = 1)
    private Long pid;

    /**
     * 名称
     */
    @ApiModelProperty(value = "功能名称", position = 2)
    @Length(max = 32, message = "功能名称长度不能大于32个字符")
    private String funcName;

}
