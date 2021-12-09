package cn.woyun.anov.random;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * RandomUtil的单元测试类。
 *
 * @author xuepeng
 */
public class RandomUtilTest {

    /**
     * 测试生成6位随机数字符串。
     */
    @Test
    public void test_generateSixDigitsString_success() {
        final String result = RandomUtil.getSixDigitsString();
        Assert.assertEquals(6, result.length());
    }

    /**
     * 测试生成UUID。
     */
    @Test
    public void test_getUUID_success() {
        final String result = RandomUtil.getUUID();
        Assert.assertEquals(36, result.length());
    }

    /**
     * 测试生成大写的UUID。
     */
    @Test
    public void test_getUpperUUID_success() {
        final String result = RandomUtil.getUpperUUID();
        Assert.assertEquals(36, result.length());
    }

    /**
     * 测试批量生成UUID。
     */
    @Test
    public void test_getUUIDs() {
        final List<String> result = RandomUtil.getUUIDs(100);
        Assert.assertEquals(100, result.size());
    }

    /**
     * 测试批量生成UUID，参数为0，抛出异常IllegalArgumentException。
     */
    @Test
    public void test_getUUIDs_exception_IllegalArgumentException() {
        try {
            RandomUtil.getUUIDs(0);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 测试批量生成大写UUID。
     */
    @Test
    public void test_getUpperUUIDs_success() {
        final List<String> result = RandomUtil.getUpperUUIDs(100);
        Assert.assertEquals(100, result.size());
    }

    /**
     * 测试生成32位UUID。
     */
    @Test
    public void test_get32UUID_success() {
        final String result = RandomUtil.get32UUID();
        Assert.assertEquals(32, result.length());
    }

    /**
     * 测试生成32位大写UUID。
     */
    @Test
    public void test_getUpper32UUID_success() {
        final String result = RandomUtil.getUpper32UUID();
        Assert.assertEquals(32, result.length());
    }

    /**
     * 测试批量获取32位的UUID。
     */
    @Test
    public void test_get32UUIDs_success() {
        final List<String> result = RandomUtil.get32UUIDs(100);
        Assert.assertEquals(100, result.size());
    }

    /**
     * 测试批量获取32位的UUID，参数为0，抛出异常IllegalArgumentException。
     */
    @Test
    public void test_get32UUIDs_exception_IllegalArgumentException() {
        try {
            RandomUtil.get32UUIDs(0);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 测试批量获取32位的大写UUID。
     */
    @Test
    public void test_getUpper32UUIDs_success() {
        final List<String> result = RandomUtil.getUpper32UUIDs(100);
        Assert.assertEquals(100, result.size());
    }

}
