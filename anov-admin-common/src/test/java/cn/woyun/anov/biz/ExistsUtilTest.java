package cn.woyun.anov.biz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ExistsUtil单元测试类。
 *
 * @author xuepeng
 */
public class ExistsUtilTest {

    /**
     * 测试用的用户集合。
     */
    private static final List<User> USERS = new ArrayList<>(10);

    /**
     * 测试用于验证某个值是否存在。
     */
    @Test
    public void test_exists_success() {
        // 存储中没有Id等于1的用户，判断Id等于1，结果为false，不存在
        final boolean result1 = ExistsUtil.exists(USERS, "1", "Id");
        Assert.assertFalse(result1);

        USERS.add(new User(1, "A"));
        // 修改时，存储中有一个Id等于1的用户，判断Id等于1，结果为false，不存在
        final boolean result2 = ExistsUtil.exists(USERS, "1", "Id");
        Assert.assertFalse(result2);

        // 修改时，存储中有一个Id等于1的用户，判断Id等于2（不等于1），结果为true，存在
        final boolean result3 = ExistsUtil.exists(USERS, "2", "Id");
        Assert.assertTrue(result3);

        // 修改时，存储中有一个Id等于1的用户，判断Id等于""，结果为true，存在
        final boolean result4 = ExistsUtil.exists(USERS, "", "Id");
        Assert.assertTrue(result4);

        // 存储中有多个Id等于1的用户，判断Id等于1，结果为true，存在
        USERS.add(new User(1, "A"));
        final boolean result5 = ExistsUtil.exists(USERS, "1", "Id");
        Assert.assertTrue(result5);

    }

    /**
     * 测试用的用户类。
     *
     * @author xuepeng
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        /**
         * 主键。
         */
        private Integer id;
        /**
         * 姓名。
         */
        private String name;
    }

}
