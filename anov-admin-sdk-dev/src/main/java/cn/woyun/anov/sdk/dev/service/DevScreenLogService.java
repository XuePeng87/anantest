package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreenLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 大屏日志的业务处理接口。
 * </p>
 *
 * @author xuepeng
 * @since 2021-12-03
 */
public interface DevScreenLogService extends IService<DevScreenLog> {

    /**
     * 创建大屏日志。
     *
     * @param devScreenLog 大屏日志对象。
     */
    void create(final DevScreenLog devScreenLog);

    /**
     * 根据条件分页查询大屏日志。
     *
     * @param page         分页信息。
     * @param devScreenLog 查询条件。
     * @return 大屏日志集合。
     */
    Page<DevScreenLog> findByPageAndCondition(final Page<DevScreenLog> page, final DevScreenLog devScreenLog);

}