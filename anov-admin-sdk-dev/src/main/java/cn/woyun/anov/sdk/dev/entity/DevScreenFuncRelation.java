package cn.woyun.anov.sdk.dev.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 大屏项目与功能的关系表 
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DevScreenFuncRelation implements Serializable {

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
     * 大屏主键
     */
    private Long screenId;

    /**
     * 功能名称
     */
    private String funcName;


}
