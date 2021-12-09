package cn.woyun.anov.jsoup;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.safety.Whitelist;
import org.junit.Assert;
import org.junit.Test;

/**
 * JsoupUtil的单元测试类。
 *
 * @author xuepeng
 */
public class JsoupUtilTest {

    /**
     * 测试用的HTML字符串。
     */
    private static final String HTML = "<h1 src=\"https:www.baidu.com\">Hello World</h1>";

    /**
     * 测试HTML标签过滤。
     */
    @Test
    public void test_clean_success() {
        final JsoupUtil jsoupUtil = new JsoupUtil();
        final String result1 = jsoupUtil.clean(StringUtils.EMPTY);
        Assert.assertTrue(StringUtils.isEmpty(result1));

        final String result2 = jsoupUtil.clean(HTML);
        Assert.assertTrue(StringUtils.indexOf(result2, "src") < 0);
    }

    /**
     * 测试HTML标签过滤。
     */
    @Test
    public void test_custom_clean_success() {
        final Whitelist whitelist = Whitelist
                .relaxed()
                .addAttributes("h1", "src");
        final JsoupUtil jsoupUtil = new JsoupUtil(whitelist);
        final String result2 = jsoupUtil.clean(HTML);
        Assert.assertTrue(StringUtils.indexOf(result2, "src") > 0);
    }

}
