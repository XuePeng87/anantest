package cn.woyun.anov.gateway.management.bean.request.mgt;

import cn.woyun.anov.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 角色的查询请求参数类。
 *
 * @author xuepeng
 */
@Data
@ToString
@ApiModel(value = "角色的查询请求参数类")
public class RoleQueryRequestBean {

    /**
     * 名称
     */
    @ApiModelProperty(value = "角色名称", position = 1)
    @Length(max = 32, message = "角色名称长度不能大于32个字符")
    private String funcName;

    /**
     * 编号
     */
    @ApiModelProperty(value = "角色编号", position = 2)
    @Length(max = 32, message = "角色编号长度不能大于32个字符")
    private String funcCode;

    /**
     * 分页信息。
     */
    @ApiModelProperty(value = "分页", required = true, position = 3)
    private PageParam page;

}
