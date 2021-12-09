package cn.woyun.anov.http;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * HttpResult的单元测试类。
 *
 * @author xuepeng
 */
public class HttpResultTest {

    /**
     * 测试Build一个HttpResult。
     */
    @Test
    public void test_buildHttpResult_success() {
        HttpResult<String> result =
                new HttpResult.Builder<String>(DefaultHttpResultStatus.SUCCESS)
                        .msg("success").data("success").build();
        Assert.assertEquals(DefaultHttpResultStatus.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(DefaultHttpResultStatus.SUCCESS.getDesc(), result.getDesc());
        Assert.assertTrue(StringUtils.equals(result.getMsg(), result.getData()));
    }

}
