package cn.woyun.anov.exception;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * ParameterException的单元测试类。
 *
 * @author xuepeng
 */
public class ParameterExceptionTest {

    /**
     * 测试实例化ParameterException。
     */
    @Test
    public void test_newParameterException_success() {
        final ParameterException ex1 = new ParameterException("Exception");
        Assert.assertTrue(StringUtils.isNotEmpty(ex1.getMessage()));

        final ParameterException ex2 = new ParameterException("NPE", new NullPointerException());
        Assert.assertTrue(StringUtils.isNotEmpty(ex2.getMessage())
                && ex2.getCause() instanceof NullPointerException);
    }

}
