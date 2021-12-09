package cn.woyun.anov.sdk.dev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 大屏监控
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DevScreenPic implements Serializable {

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
     * 大屏唯一标识
     */
    private String screenKey;

    /**
     * 监控页面
     */
    private String monitorPage;

    /**
     * 监控截图
     */
    private String monitorImg;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
