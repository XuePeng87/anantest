package cn.woyun.anov.biz;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 树结构转换工具类。
 * 对实现TreeStructDescription接口的List对象，进行树结构转换。
 *
 * @author xuepeng
 */
public class TreeUtil {

    /**
     * 构造函数。
     */
    private TreeUtil() {
    }

    /**
     * 转换树结构。
     *
     * @param list    要转换的集合。
     * @param rootVal 跟节点的值。
     * @param <T>     实现TreeStructDescription的类。
     * @return 转换后的树型结构。
     */
    public static <T extends TreeStructDescription> List<T> format(final List<T> list, final String rootVal) {
        // 获取所有一级对象
        final List<T> roots = list.stream()
                .filter(item -> StringUtils.equals(item.getNodePid(), rootVal))
                .collect(Collectors.toList());
        // 递归生成树结构
        roots.forEach(root -> tree(root, list));
        return roots;
    }

    /**
     * 递归生成树结构。
     *
     * @param parent 根节点。
     * @param nodes  要转换的结合。
     * @param <T>    实现TreeStructDescription的类。
     */
    private static <T extends TreeStructDescription> void tree(final T parent, final List<T> nodes) {
        nodes.stream()
                .filter(node -> StringUtils.equals(node.getNodePid(), parent.getNodeId()))
                .forEach(node -> {
                    parent.addChild(node);
                    tree(node, nodes);
                });
    }

}
