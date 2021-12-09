package cn.woyun.anov.gateway.management.bean.response.mgt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户的响应数据类。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value = "用户的响应数据类")
public class UserResponseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。
     */
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    /**
     * 用户所属部门。
     */
    @ApiModelProperty(value = "用户所属部门", position = 2)
    private Long deptId;

    /**
     * 用户所属部门名称。
     */
    @ApiModelProperty(value = "用户所属部门名称", position = 3)
    private String deptName;

    /**
     * 账号。
     */
    @ApiModelProperty(value = "帐号", position = 4)
    private String userAccount;

    /**
     * 姓名。
     */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /**
     * 电话。
     */
    @ApiModelProperty(value = "电话", position = 6)
    private String userPhone;

    /**
     * 邮箱。
     */
    @ApiModelProperty(value = "邮箱", position = 7)
    private String userEmail;

    /**
     * 备注。
     */
    @ApiModelProperty(value = "创建时间", position = 8)
    private LocalDateTime createTime;

    /**
     * 角色。
     */
    @ApiModelProperty(value = "拥有的角色", position = 9)
    private transient List<RoleResponseBean> roles = new ArrayList<>();

    /**
     * 部门。
     */
    @ApiModelProperty(value = "所属的部门", position = 10)
    private transient DeptResponseBean dept = new DeptResponseBean();

    /**
     * 功能。
     */
    @ApiModelProperty(value = "拥有的功能", position = 11)
    private transient List<FuncResponseBean> funcs = new ArrayList<>();

    /**
     * 租户。
     */
    @ApiModelProperty(value = "所属的功能", position = 12)
    private transient TenantResponseBean tenant = new TenantResponseBean();

}
