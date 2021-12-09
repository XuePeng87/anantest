package cn.woyun.anov.page;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderInfoTest的单元测试类。
 *
 * @author xuepeng
 */
public class PageResultTest {

    /**
     * 测试创建一个OrderInfo。
     */
    @Test
    public void test_newPageResult_success() {
        final List<String> record = new ArrayList<>();
        record.add("hello");
        PageResult<String> result = new PageResult<>(
                1, 10, 100, record
        );
        Assert.assertEquals(1, result.getCurrentPage());
        Assert.assertEquals(10, result.getPageSize());
        Assert.assertEquals(100, result.getTotalCount());
        Assert.assertEquals("hello", result.getRecord().get(0));
    }

}
