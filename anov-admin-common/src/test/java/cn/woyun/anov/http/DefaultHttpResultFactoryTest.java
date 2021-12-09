package cn.woyun.anov.http;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * DefaultHttpResultFactory的单元测试类。
 *
 * @author xuepeng
 */
public class DefaultHttpResultFactoryTest {

    /**
     * 测试创建success的HttpResult。
     */
    @Test
    public void test_success_success() {
        final HttpResult<Map<String, String>> result1 =
                DefaultHttpResultFactory.success();
        Assert.assertTrue(StringUtils.isEmpty(result1.getMsg()));

        final HttpResult<Map<String, String>> result2 =
                DefaultHttpResultFactory.success("msg");
        Assert.assertTrue(StringUtils.isNotEmpty(result2.getMsg()));

        final HttpResult<String> result3 =
                DefaultHttpResultFactory.success("test", "test");
        Assert.assertTrue(StringUtils.equals(result3.getMsg(), result3.getData()));
    }

    /**
     * 测试创建timeout的HttpResult。
     */
    @Test
    public void test_timeout_success() {
        final HttpResult<Map<String, String>> result1 =
                DefaultHttpResultFactory.timeout();
        Assert.assertTrue(StringUtils.isEmpty(result1.getMsg()));

        final HttpResult<Map<String, String>> result2 =
                DefaultHttpResultFactory.timeout("msg");
        Assert.assertTrue(StringUtils.isNotEmpty(result2.getMsg()));

        final HttpResult<String> result3 =
                DefaultHttpResultFactory.timeout("test", "test");
        Assert.assertTrue(StringUtils.equals(result3.getMsg(), result3.getData()));
    }

    /**
     * 测试创建param的HttpResult。
     */
    @Test
    public void test_param_success() {
        final HttpResult<Map<String, String>> result1 =
                DefaultHttpResultFactory.param();
        Assert.assertTrue(StringUtils.isEmpty(result1.getMsg()));

        final HttpResult<Map<String, String>> result2 =
                DefaultHttpResultFactory.param("msg");
        Assert.assertTrue(StringUtils.isNotEmpty(result2.getMsg()));

        final HttpResult<String> result3 =
                DefaultHttpResultFactory.param("test", "test");
        Assert.assertTrue(StringUtils.equals(result3.getMsg(), result3.getData()));
    }

    /**
     * 测试创建param的HttpResult。
     */
    @Test
    public void test_fail_success() {
        final HttpResult<Map<String, String>> result1 =
                DefaultHttpResultFactory.fail();
        Assert.assertTrue(StringUtils.isEmpty(result1.getMsg()));

        final HttpResult<Map<String, String>> result2 =
                DefaultHttpResultFactory.fail("msg");
        Assert.assertTrue(StringUtils.isNotEmpty(result2.getMsg()));

        final HttpResult<String> result3 =
                DefaultHttpResultFactory.fail("test", "test");
        Assert.assertTrue(StringUtils.equals(result3.getMsg(), result3.getData()));
    }

    /**
     * 测试创建permissions的HttpResult。
     */
    @Test
    public void test_permissions_success() {
        final HttpResult<Map<String, String>> result1 =
                DefaultHttpResultFactory.permissions();
        Assert.assertTrue(StringUtils.isEmpty(result1.getMsg()));

        final HttpResult<Map<String, String>> result2 =
                DefaultHttpResultFactory.permissions("msg");
        Assert.assertTrue(StringUtils.isNotEmpty(result2.getMsg()));

        final HttpResult<String> result3 =
                DefaultHttpResultFactory.permissions("test", "test");
        Assert.assertTrue(StringUtils.equals(result3.getMsg(), result3.getData()));
    }

    /**
     * 测试创建error的HttpResult。
     */
    @Test
    public void test_error_success() {
        final HttpResult<Map<String, String>> result1 =
                DefaultHttpResultFactory.error();
        Assert.assertTrue(StringUtils.isEmpty(result1.getMsg()));

        final HttpResult<Map<String, String>> result2 =
                DefaultHttpResultFactory.error("msg");
        Assert.assertTrue(StringUtils.isNotEmpty(result2.getMsg()));

        final HttpResult<String> result3 =
                DefaultHttpResultFactory.error("test", "test");
        Assert.assertTrue(StringUtils.equals(result3.getMsg(), result3.getData()));
    }

}
