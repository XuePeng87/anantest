package cn.woyun.anov.sdk.dev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 大屏项目表
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DevScreen implements Serializable {

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
     * 名称
     */
    private String screenName;

    /**
     * 编号
     */
    private String screenCode;

    /**
     * 标识
     */
    private String screenKey;

    /**
     * 有效期
     */
    private LocalDateTime screenExpireTime;

    /**
     * 监控的首张图片。
     */
    private String screenMonitorPic;

    /**
     * 备注
     */
    private String screenRemark;

    /**
     * 状态 0=停用；1=启用；
     */
    private Integer screenStatus;

    /**
     * 是否需要认证
     */
    private Boolean screenAuthenticated;

    /**
     * 地址
     */
    private String screenUrl;

    /**
     * 是否可预览
     */
    private Boolean screenPreviewed;

    /**
     * 验证次数
     */
    private Integer screenCheckCount;

    /**
     * 芯片
     */
    private byte[] screenCore;

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
     * 功能。
     */
    @TableField(exist = false)
    private Collection<String> funcs = new ArrayList<>();

    /**
     * 图片。
     */
    @TableField(exist = false)
    private List<DevScreenPic> pics;

    /**
     * 部门名称。
     */
    @TableField(exist = false)
    private String deptName;

}
