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
 * 系统功能项表
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFunc implements Serializable, TreeStructDescription {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级功能项
     */
    private Long pid;

    /**
     * 名称
     */
    private String funcName;

    /**
     * 编号
     */
    private String funcCode;

    /**
     * 类型 0=目录，1=菜单；2=按钮
     */
    private Integer funcType;

    /**
     * 图标
     */
    private String funcIcon;

    /**
     * 路由地址
     */
    private String funcPath;

    /**
     * 是否外链
     */
    private Boolean funcLinkable;

    /**
     * 是否缓存
     */
    private Boolean funcCacheable;

    /**
     * 是否可见
     */
    private Boolean funcVisible;

    /**
     * 组件名称
     */
    private String funcComponentName;

    /**
     * 组件地址
     */
    private String funcComponentPath;

    /**
     * 排序
     */
    private Integer funcSort;

    /**
     * 备注
     */
    private String funcRemark;

    /**
     * 是否内置
     */
    private Boolean fixed;

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
    private List<SysFunc> children = new ArrayList<>();

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
        getChildren().add((SysFunc) node);
    }

}
