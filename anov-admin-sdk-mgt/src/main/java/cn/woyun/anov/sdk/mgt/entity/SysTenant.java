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
 * 系统租户表
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysTenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String tenantName;

    /**
     * 手机
     */
    private String tenantPhone;

    /**
     * 邮箱
     */
    private String tenantEmail;

    /**
     * 级别 0=试用；1=正式；2=系统。
     */
    private Integer tenantLevel;

    /**
     * 状态 0=停用；1=启用
     */
    private Integer tenantStatus;

    /**
     * 来源 0=自主，1=系统
     */
    private Integer tenantOrigin;

    /**
     * 有效期
     */
    private LocalDateTime tenantExpireTime;

    /**
     * 备注
     */
    private String tenantRemark;

    /**
     * 是否删除
     */
    private Boolean deleted;

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
     * 管理帐号。
     */
    @TableField(exist = false)
    private String userAccount;

    /**
     * 开通租户时的角色。
     */
    @TableField(exist = false)
    private List<Long> roleIds = new ArrayList<>();

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
