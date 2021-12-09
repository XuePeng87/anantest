package cn.woyun.anov.exception;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * BaseException的单元测试类。
 *
 * @author xuepeng
 */
public class BaseExceptionTest {

    /**
     * 测试实例化BaseException。
     */
    @Test
    public void test_newBaseException_success() {
        final BaseException baseException1 = new BaseException();
        Assert.assertTrue(StringUtils.isEmpty(baseException1.getMessage()));

        final BaseException baseException2 = new BaseException("BaseException");
        Assert.assertTrue(StringUtils.isNotEmpty(baseException2.getMessage()));

        final BaseException baseException3 = new BaseException(new NullPointerException());
        Assert.assertTrue(baseException3.getCause() instanceof NullPointerException);

        final BaseException baseException4 = new BaseException("NPE", new NullPointerException());
        Assert.assertTrue(StringUtils.isNotEmpty(baseException4.getMessage())
                && baseException4.getCause() instanceof NullPointerException);
    }

}
