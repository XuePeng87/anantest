package cn.woyun.anov.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

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
     * 部门主键
     */
    private Long deptId;

    /**
     * 帐号
     */
    private String userAccount;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 电话
     */
    private String userPhone;

    /**
     * 状态 0=停用；1=启用
     */
    private Integer userStatus;

    /**
     * 备注
     */
    private String userRemark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 角色。
     */
    @TableField(exist = false)
    private transient List<SysRole> roles = new ArrayList<>();

    /**
     * 部门。
     */
    @TableField(exist = false)
    private transient SysDept dept = new SysDept();

    /**
     * 功能。
     */
    @TableField(exist = false)
    private transient List<SysFunc> funcs = new ArrayList<>();

    /**
     * 租户。
     */
    @TableField(exist = false)
    private transient SysTenant tenant = new SysTenant();

}
