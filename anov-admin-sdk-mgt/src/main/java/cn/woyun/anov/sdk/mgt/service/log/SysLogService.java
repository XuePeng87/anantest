package cn.woyun.anov.sdk.mgt.service.log;

import cn.woyun.anov.sdk.mgt.entity.SysLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 日志的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 创建日志。
     *
     * @param sysLog 日志实体类。
     */
    void create(final SysLog sysLog);

    /**
     * 根据条件分页查询日志。
     *
     * @param page   分页信息。
     * @param sysLog 查询条件。
     * @return 日志信息集合。
     */
    Page<SysLog> findByPageAndCondition(final Page<SysLog> page, final SysLog sysLog);

}