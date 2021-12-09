package cn.woyun.anov.jwt;

import cn.woyun.anov.json.JsonUtilTest;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * JWTUtil的单元测试类。
 *
 * @author xuepeng
 */
public class JWTUtilTest {

    /**
     * 测试用的用户对象。
     */
    private static final JsonUtilTest.User USER = new JsonUtilTest.User();

    private static final String SECRET = "test@123";

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
     * 测试创建JWT。
     */
    @Test
    public void test_create_success() {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", USER.getId());
        final String result = JWTUtil.create(claims, SECRET);
        System.out.println(result);
        Assert.assertTrue(StringUtils.isNotEmpty(result));
    }

    /**
     * 测试创建JWT，参数为空，抛出NullPointerException。
     */
    @Test
    public void test_create_exception_NullPointerException() {
        try {
            JWTUtil.create(null, SECRET);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }
        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", USER.getId());
        try {
            JWTUtil.create(claims, null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * 测试获取JWT中的数据。
     */
    @Test
    public void test_get_success() {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", USER.getId());
        final String jwt = JWTUtil.create(claims, SECRET);
        final Integer id = JWTUtil.get(jwt, SECRET, "id", Integer.class);
        Assert.assertEquals(USER.getId(), id);
    }

    /**
     * 测试获取JWT中的数据，参数为空，抛出NullPointerException。
     */
    @Test
    public void test_get_exception_NullPointerException() {
        try {
            JWTUtil.get("", SECRET, "id", Integer.class);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }
        try {
            JWTUtil.get("test", null, "id", Integer.class);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }
        try {
            JWTUtil.get("test", SECRET, null, Integer.class);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
        }
    }

    /**
     * 测试验证JWT中的参数是否正确。
     */
    @Test
    public void test_verify_success() {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", USER.getId());
        final String result = JWTUtil.create(claims, SECRET);
        Assert.assertTrue(JWTUtil.verify(result, SECRET, "id", USER.getId(), Integer.class));
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

}
