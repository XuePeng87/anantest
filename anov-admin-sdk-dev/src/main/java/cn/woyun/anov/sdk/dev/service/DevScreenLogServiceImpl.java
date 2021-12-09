package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreenLog;
import cn.woyun.anov.sdk.dev.mapper.DevScreenLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 大屏日志的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-12-03
 */
@Service
public class DevScreenLogServiceImpl extends ServiceImpl<DevScreenLogMapper, DevScreenLog> implements DevScreenLogService {

    /**
     * 创建大屏日志。
     *
     * @param devScreenLog 大屏日志对象。
     */
    @Override
    @Async
    public void create(final DevScreenLog devScreenLog) {
        devScreenLog.setCreateTime(LocalDateTime.now());
        super.save(devScreenLog);
    }

    /**
     * 根据条件分页查询大屏日志。
     *
     * @param page         分页信息。
     * @param devScreenLog 查询条件。
     * @return 大屏日志集合。
     */
    @Override
    public Page<DevScreenLog> findByPageAndCondition(final Page<DevScreenLog> page, final DevScreenLog devScreenLog) {
        final QueryWrapper<DevScreenLog> wrapper = new QueryWrapper<>();
        final LambdaQueryWrapper<DevScreenLog> criteria = wrapper.lambda();
        if (StringUtils.isNotBlank(devScreenLog.getScreenKey())) {
            criteria.eq(DevScreenLog::getScreenKey, devScreenLog.getScreenKey());
        }
        if (StringUtils.isNotBlank(devScreenLog.getClientCode())) {
            criteria.eq(DevScreenLog::getClientCode, devScreenLog.getClientCode());
        }
        if (StringUtils.isNotBlank(devScreenLog.getLogModule())) {
            criteria.eq(DevScreenLog::getLogModule, devScreenLog.getLogModule());
        }
        if (StringUtils.isNotBlank(devScreenLog.getLogType())) {
            criteria.eq(DevScreenLog::getLogType, devScreenLog.getLogType());
        }
        if (ObjectUtils.allNotNull(devScreenLog.getBeginTime(), devScreenLog.getEndTime())) {
            criteria.between(DevScreenLog::getCreateTime, devScreenLog.getBeginTime(), devScreenLog.getEndTime());
        }
        if (StringUtils.isNotBlank(devScreenLog.getLogDesc())) {
            criteria.like(DevScreenLog::getLogDesc, devScreenLog.getLogDesc());
        }
        criteria.orderByDesc(DevScreenLog::getCreateTime);
        return super.page(page, wrapper);
    }

}