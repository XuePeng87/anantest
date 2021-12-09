package cn.woyun.anov.biz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeUtil单元测试类。
 *
 * @author xuepeng
 */
public class TreeUtilTest {

    /**
     * 树形结构对象。
     */
    private static final List<Menu> MENUS = new ArrayList<>();

    /**
     * 测试前初始化数据。
     */
    @Before
    public void before() {
        MENUS.add(Menu.builder().id(1).name("1").pid(0).children(new ArrayList<>()).build());
        MENUS.add(Menu.builder().id(2).name("1-1").pid(1).children(new ArrayList<>()).build());
        MENUS.add(Menu.builder().id(3).name("1-2").pid(1).children(new ArrayList<>()).build());
        MENUS.add(Menu.builder().id(4).name("2").pid(0).children(new ArrayList<>()).build());
        MENUS.add(Menu.builder().id(5).name("2-1").pid(4).children(new ArrayList<>()).build());
        MENUS.add(Menu.builder().id(6).name("2-2").pid(4).children(new ArrayList<>()).build());
    }

    /**
     * 测试树结构转换。
     */
    @Test
    public void test_format_success() {
        List<Menu> result = TreeUtil.format(MENUS, "0");
        Assert.assertEquals(2, result.get(0).getChildren().size());
    }

    /**
     * 测试用的树形结构对象。
     *
     * @author xuepeng
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Menu implements TreeStructDescription {
        /**
         * 主键。
         */
        private Integer id;
        /**
         * 名称。
         */
        private String name;
        /**
         * 父级主键。
         */
        private Integer pid;
        /**
         * 子节点。
         */
        private List<Menu> children = new ArrayList<>();

        /**
         * @return 获取树节点的Id。
         */
        @Override
        public String getNodeId() {
            return getId().toString();
        }

        /**
         * @return 获取树节点的Pid。
         */
        @Override
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
            getChildren().add((Menu) node);
        }

    }

}
