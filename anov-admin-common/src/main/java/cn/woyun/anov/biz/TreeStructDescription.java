package cn.woyun.anov.biz;

/**
 * 树结构的描述接口。
 * 实现该接口的类，可以通过Id，Pid组成一个树形结构。
 * 并实现getNodeId，getNodePid和addChild方法。
 * 随后可以使用TreeUtil类，对实现的List进行树形结构转换。
 *
 * @author xuepeng
 */
public interface TreeStructDescription {

    /**
     * @return 获取树节点的Id。
     */
    String getNodeId();

    /**
     * @return 获取树节点的Pid。
     */
    String getNodePid();

    /**
     * 添加树的子节点。
     *
     * @param node 子节点。
     */
    void addChild(final TreeStructDescription node);

}
