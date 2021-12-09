package cn.woyun.anov.gateway.management.controller.mgt;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.bean.request.mgt.LogQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.response.mgt.LogResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.page.PageResult;
import cn.woyun.anov.sdk.mgt.entity.SysLog;
import cn.woyun.anov.sdk.mgt.service.log.SysLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 日志的API Controller。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/logs")
@Api(tags = "日志API接口")
public class LogController extends BaseController {

    /**
     * 根据条件分页查询日志。
     *
     * @param logQueryRequestBean 日志的查询请求参数类
     * @return 日志集合。
     */
    @PostMapping("/v1/conditions")
    @ApiOperation(value = "根据条件分页查询日志")
    public HttpResult<PageResult<LogResponseBean>> findByConditionAndPage(@Valid @RequestBody final LogQueryRequestBean logQueryRequestBean) {
        final SysLog sysLog = BeanUtil.objToObj(logQueryRequestBean, SysLog.class);
        final Page<SysLog> page = pageParamToPage(logQueryRequestBean.getPage());
        final Page<SysLog> result = sysLogService.findByPageAndCondition(page, sysLog);
        return DefaultHttpResultFactory.success(
                "根据条件分页查询日志成功。",
                pageToPageResult(result, LogResponseBean.class)
        );
    }

    /**
     * 自动装配日志的业务处理接口。
     *
     * @param sysLogService 日志的业务处理接口。
     */
    @Autowired
    public void setSysLogService(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 日志的业务处理接口。
     */
    private SysLogService sysLogService;

}
