package cn.woyun.anov.exception;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * TimeoutException的单元测试类。
 *
 * @author xuepeng
 */
public class TimeoutExceptionTest {

    /**
     * 测试实例化TimeoutException。
     */
    @Test
    public void test_newTimeoutException_success() {
        final TimeoutException ex1 = new TimeoutException("Exception");
        Assert.assertTrue(StringUtils.isNotEmpty(ex1.getMessage()));

        final TimeoutException ex2 = new TimeoutException("NPE", new NullPointerException());
        Assert.assertTrue(StringUtils.isNotEmpty(ex2.getMessage())
                && ex2.getCause() instanceof NullPointerException);
    }

}
