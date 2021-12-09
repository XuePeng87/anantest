package cn.woyun.anov.page;

import java.util.List;

/**
 * 使用框架提供的Mybatis提供的BaseDao做分页查询时，需要使用本类保存查询结果。
 * 本类保存的查询结果信息有，当前页数、每页显示的条数、总条数、总页数和当前页数据的List集合。
 * 本类采用此方式定义数据结果，希望能满足大部分调用者的DataGrid展示分页信息与数据。
 *
 * @param <T> 实现本接口的泛型类（Model）。
 * @author xuepeng
 */
public class PageResult<T> {

    /**
     * 构造函数。
     *
     * @param currentPage 当前页数。
     * @param pageSize    每页显示行数。
     * @param totalCount  总页数。
     * @param record      本页的数据列表。
     */
    public PageResult(long currentPage, long pageSize, long totalCount, List<T> record) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        // 计算总页码
        pageCount = (totalCount + pageSize - 1) / pageSize;
        this.record = record;
    }

    /**
     * @return 获取当前页数。
     */
    public long getCurrentPage() {
        return currentPage;
    }

    /**
     * @return 获取每页显示多少条。
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * @return 总记录数。
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * @return 总页数。
     */
    public long getPageCount() {
        return pageCount;
    }

    /**
     * @return 本页的数据列表。
     */
    public List<T> getRecord() {
        return record;
    }

    /**
     * 当前页数。
     */
    private final long currentPage;
    /**
     * 每页显示多少条。
     */
    private final long pageSize;
    /**
     * 总记录数。
     */
    private final long totalCount;
    /**
     * 总页数。
     */
    private final long pageCount;
    /**
     * 本页的数据列表。
     */
    private final List<T> record;

}