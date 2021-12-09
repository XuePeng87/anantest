package cn.woyun.anov.exception;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * ServiceException的单元测试类。
 *
 * @author xuepeng
 */
public class ServiceExceptionTest {

    /**
     * 测试实例化ServiceException。
     */
    @Test
    public void test_newServiceException_success() {
        final ServiceException ex1 = new ServiceException("Exception");
        Assert.assertTrue(StringUtils.isNotEmpty(ex1.getMessage()));

        final ServiceException ex2 = new ServiceException("NPE", new NullPointerException());
        Assert.assertTrue(StringUtils.isNotEmpty(ex2.getMessage())
                && ex2.getCause() instanceof NullPointerException);
    }

}
