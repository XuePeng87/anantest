package cn.woyun.anov.date;

import org.junit.Assert;
import org.junit.Test;

/**
 * DateTimeUtil的单元测试类。
 *
 * @author xuepeng
 */
public class DateTimeUtilTest {

    /**
     * 测试获取从现在到明天的秒数。
     */
    @Test
    public void test_getSecoudFromNowToTomorrow_success() {
        final long result = DateTimeUtil.getSecoudFromNowToTomorrow();
        System.out.println(result);
        Assert.assertTrue(result > 0);
    }

}
