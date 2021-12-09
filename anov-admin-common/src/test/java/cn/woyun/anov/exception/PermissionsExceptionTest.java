package cn.woyun.anov.exception;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * PermissionsException的单元测试类。
 *
 * @author xuepeng
 */
public class PermissionsExceptionTest {

    /**
     * 测试实例化PermissionsException。
     */
    @Test
    public void test_newPermissionsException_success() {
        final PermissionsException ex1 = new PermissionsException("Exception");
        Assert.assertTrue(StringUtils.isNotEmpty(ex1.getMessage()));

        final PermissionsException ex2 = new PermissionsException("NPE", new NullPointerException());
        Assert.assertTrue(StringUtils.isNotEmpty(ex2.getMessage())
                && ex2.getCause() instanceof NullPointerException);
    }

}
