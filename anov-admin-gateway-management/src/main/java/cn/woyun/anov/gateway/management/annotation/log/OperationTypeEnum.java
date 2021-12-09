package cn.woyun.anov.gateway.management.annotation.log;

/**
 * 操作日志类型。
 *
 * @author xuepeng
 */
public enum OperationTypeEnum {

    /**
     * 其它
     */
    OTHER,
    /**
     * 创建
     */
    CREATE,
    /**
     * 修改
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 查询
     */
    QUERY,
    /**
     * 详情
     */
    DETAIL,
    /**
     * 树
     */
    TREE,
    /**
     * 导入
     */
    IMPORT,
    /**
     * 导出
     */
    EXPORT,
    /**
     * 授权
     */
    GRANT,
    /**
     * 强退
     */
    FORCE,
    /**
     * 清空
     */
    CLEAN,
    /**
     * 修改状态
     */
    CHANGE_STATUS

}
