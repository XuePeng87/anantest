package cn.woyun.anov.sdk.mgt.entity;

import cn.woyun.anov.biz.TreeStructDescription;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统组织机构表
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDept implements Serializable, TreeStructDescription {

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
     * 上级部门
     */
    private Long pid;

    /**
     * 名称
     */
    private String deptName;

    /**
     * 别名
     */
    private String deptAlias;

    /**
     * 编号
     */
    private String deptCode;

    /**
     * 图标
     */
    private String deptIcon;

    /**
     * 排序
     */
    private Integer deptSort;

    /**
     * 状态  0=停用；1=启用
     */
    private Integer deptStatus;

    /**
     * 备注
     */
    private String deptRemark;

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
     * 子节点。
     */
    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<>();

    /**
     * @return 获取树节点的Id。
     */
    @Override
    @JsonIgnore
    public String getNodeId() {
        return getId().toString();
    }

    /**
     * @return 获取树节点的Pid。
     */
    @Override
    @JsonIgnore
    public String getNodePid() {
        return getPid().toString();
    }

    /**
     * 添加树的子节点。
     *
     * @param node 子节点。
     */
    @Override
    public void addChild(final TreeStructDescription node) {
        getChildren().add((SysDept) node);
    }
}
