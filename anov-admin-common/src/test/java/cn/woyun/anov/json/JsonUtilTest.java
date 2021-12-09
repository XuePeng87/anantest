package cn.woyun.anov.json;

import lombok.Data;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * JsonUtil单元测试类。
 *
 * @author xuepeng
 */
public class JsonUtilTest {

    /**
     * 测试用的用户对象。
     */
    private static final User USER = new User();

    /**
     * 测试用的Json字符串。
     */
    private static final String JSON_STR = "{\"id\":1,\"name\":\"Jack\",\"birthday\":1635356040683}";

    /**
     * 测试前初始化用户对象。
     */
    @Before
    public void before() {
        USER.setId(1);
        USER.setName("Jack");
        USER.setBirthday(LocalDateTime.now());
    }

    /**
     * 测试对象转Json字符串。
     */
    @Test
    public void test_convertObj2Str_success() {
        // 对象转字符串
        final String jsonStr1 = JsonUtil.convertObj2Str(USER);
        System.out.println(jsonStr1);
        Assert.assertNotNull(jsonStr1);
        // 字符串转字符串
        final String jsonStr2 = JsonUtil.convertObj2Str(JSON_STR);
        System.out.println(jsonStr2);
        Assert.assertEquals(JSON_STR, jsonStr2);
    }

    /**
     * 测试对象转Json字符串，传入空对象，抛出NullPointerException。
     */
    @Test
    public void test_convertObj2Str_exception_NullPointerException() {
        try {
            JsonUtil.convertObj2Str(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * 测试对象转Json字符串，转换失败，抛出IllegalArgumentException。
     */
    @Test
    public void test_convertObj2Str_exception_IllegalArgumentException() {
        try {
            final String jsonStr = JsonUtil.convertObj2Str(new Role());
            System.out.println(jsonStr);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 测试Json字符串转对象。
     */
    @Test
    public void test_convertStr2Obj_success() {
        final User user = JsonUtil.convertStr2Obj(JSON_STR, User.class);
        System.out.println(user);
        Assert.assertEquals("Jack", user.getName());
    }

    /**
     * 测试Json字符串转对象，抛出转换失败异常。
     */
    @Test
    public void test_convertStr2Obj_exception() {
        try {
            JsonUtil.convertStr2Obj("asd", User.class);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 测试用的用户类。
     *
     * @author xuepeng
     */
    @Data
    @ToString
    public static class User {
        /**
         * 主键。
         */
        private Integer id;
        /**
         * 姓名。
         */
        private String name;
        /**
         * 生日。
         */
        private LocalDateTime birthday;
    }


    /**
     * 测试用的角色类。
     *
     * @author xuepeng
     */
    public static class Role {

        /**
         * 角色编号。
         */
        private Integer roleCode;

    }

}
