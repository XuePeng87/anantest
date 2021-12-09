package cn.woyun.anov.exception;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * LoadPropertiesException的单元测试类。
 *
 * @author xuepeng
 */
public class LoadPropertiesExceptionTest {

    /**
     * 测试实例化LoadPropertiesException。
     */
    @Test
    public void test_newLoadPropertiesException_success() {
        final LoadPropertiesException ex1 = new LoadPropertiesException("Exception");
        Assert.assertTrue(StringUtils.isNotEmpty(ex1.getMessage()));

        final LoadPropertiesException ex2 = new LoadPropertiesException("NPE", new NullPointerException());
        Assert.assertTrue(StringUtils.isNotEmpty(ex2.getMessage())
                && ex2.getCause() instanceof NullPointerException);
    }

}
