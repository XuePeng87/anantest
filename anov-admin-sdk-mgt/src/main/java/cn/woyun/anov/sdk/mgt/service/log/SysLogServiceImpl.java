package cn.woyun.anov.sdk.mgt.service.log;

import cn.woyun.anov.sdk.mgt.entity.SysLog;
import cn.woyun.anov.sdk.mgt.mapper.SysLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    /**
     * 创建日志。
     *
     * @param sysLog 日志实体类。
     */
    @Override
    public void create(final SysLog sysLog) {
        super.save(sysLog);
    }

    /**
     * 根据条件分页查询日志。
     *
     * @param page   分页信息。
     * @param sysLog 查询条件。
     * @return 日志信息集合。
     */
    @Override
    public Page<SysLog> findByPageAndCondition(final Page<SysLog> page, final SysLog sysLog) {
        final QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        final LambdaQueryWrapper<SysLog> criteria = queryWrapper.lambda();
        if (StringUtils.isNotBlank(sysLog.getLogSystem())) {
            criteria.like(SysLog::getLogSystem, sysLog.getLogSystem());
        }
        if (StringUtils.isNotBlank(sysLog.getLogModule())) {
            criteria.like(SysLog::getLogModule, sysLog.getLogModule());
        }
        if (StringUtils.isNotBlank(sysLog.getLogType())) {
            criteria.eq(SysLog::getLogType, sysLog.getLogType());
        }
        if (StringUtils.isNotBlank(sysLog.getLogIp())) {
            criteria.like(SysLog::getLogIp, sysLog.getLogIp());
        }
        if (StringUtils.isNotBlank(sysLog.getLogReqUrl())) {
            criteria.like(SysLog::getLogReqUrl, sysLog.getLogReqUrl());
        }
        if (StringUtils.isNotBlank(sysLog.getLogReqBrowser())) {
            criteria.eq(SysLog::getLogReqBrowser, sysLog.getLogReqBrowser());
        }
        if (StringUtils.isNotBlank(sysLog.getLogClassName())) {
            criteria.like(SysLog::getLogClassName, sysLog.getLogClassName());
        }
        if (StringUtils.isNotBlank(sysLog.getLogMethodName())) {
            criteria.like(SysLog::getLogMethodName, sysLog.getLogMethodName());
        }
        if (StringUtils.isNotBlank(sysLog.getCreateUser())) {
            criteria.like(SysLog::getCreateUser, sysLog.getCreateUser());
        }
        if (ObjectUtils.allNotNull(sysLog.getBeginTime(), sysLog.getEndTime())) {
            criteria.between(SysLog::getCreateTime, sysLog.getBeginTime(), sysLog.getEndTime());
        }
        criteria.orderByDesc(SysLog::getCreateTime);
        return super.page(page, queryWrapper);
    }

}