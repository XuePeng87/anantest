package cn.woyun.anov.gateway.management.controller;

import cn.woyun.anov.json.JsonMapper;
import cn.woyun.anov.page.OrderInfo;
import cn.woyun.anov.page.PageParam;
import cn.woyun.anov.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有业务Controller的父类。
 *
 * @author xuepeng
 */
@RestController
public class BaseController {

    /**
     * @return 获取HttpServletRequest。
     */
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    /**
     * 将PageParam转换为Page。
     *
     * @param pageParam 请求分页参数。
     * @param <T>       Page对象的泛型类型。
     * @return Page对象。
     */
    public <T> Page<T> pageParamToPage(final PageParam pageParam) {
        final Page<T> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        if (CollectionUtils.isNotEmpty(pageParam.getOrders())) {
            for (OrderInfo order : pageParam.getOrders()) {
                page.getOrders().add(new OrderItem(order.getColumn(), order.getAsc()));
            }
        }
        return page;
    }

    /**
     * 将Page对象转换为PageResult对象。
     *
     * @param t1  Page对象。
     * @param k1  返回值Response对象。
     * @param <T> Page中结果的泛型对象类型。
     * @param <K> PageResult中结果的泛型对象类型。
     * @return PageResult对象。
     */
    public <T, K> PageResult<K> pageToPageResult(final Page<T> t1, Class<K> k1) {
        try {
            final ObjectMapper mapper = JsonMapper.INSTANCE.getInstance();
            if (t1 == null) {
                return null;
            }
            final List<T> list = t1.getRecords();
            final List<K> record = new ArrayList<>();
            for (T t : list) {
                final String json = mapper.writeValueAsString(t);
                record.add(mapper.readValue(json, k1));
            }
            return new PageResult<>(t1.getCurrent(), t1.getSize(), t1.getTotal(), record);
        } catch (IOException e) {
            throw new IllegalArgumentException("Page对象转换异常", e);
        }
    }

    /**
     * 设置HttpServletRequest。
     *
     * @param httpServletRequest HttpServletRequest。
     */
    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * HttpServletRequest。
     */
    private HttpServletRequest httpServletRequest;

}
