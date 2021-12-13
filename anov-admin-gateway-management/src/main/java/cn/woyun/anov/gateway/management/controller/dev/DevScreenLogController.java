package cn.woyun.anov.gateway.management.controller.dev;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.bean.request.dev.DevScreenLogQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.response.dev.DevScreenLogResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.page.PageResult;
import cn.woyun.anov.sdk.dev.entity.DevScreenLog;
import cn.woyun.anov.sdk.dev.service.DevScreenLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 大屏日志API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/devs/logs")
@Api(tags = "大屏日志API接口")
@Slf4j
@SaCheckLogin
public class DevScreenLogController extends BaseController {

    /**
     * 根据条件分页查询大屏日志。
     *
     * @param devScreenLogQueryRequestBean 大屏日志的查询请求参数类。
     * @return 日志集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "大屏日志", description = "根据条件分页查询大屏日志", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件分页查询大屏日志")
    public HttpResult<PageResult<DevScreenLogResponseBean>> findByConditionAndPage(@Valid @RequestBody final DevScreenLogQueryRequestBean devScreenLogQueryRequestBean) {
        final DevScreenLog devScreenLog = BeanUtil.objToObj(devScreenLogQueryRequestBean, DevScreenLog.class);
        final Page<DevScreenLog> page = pageParamToPage(devScreenLogQueryRequestBean.getPage());
        final Page<DevScreenLog> devScreenLogs = devScreenLogService.findByPageAndCondition(page, devScreenLog);
        final PageResult<DevScreenLogResponseBean> result = pageToPageResult(devScreenLogs, DevScreenLogResponseBean.class);
        return DefaultHttpResultFactory.success("根据条件分页查询大屏日志成功。", result);
    }

    /**
     * 自动装配大屏日志的业务处理接口。
     *
     * @param devScreenLogService 大屏日志的业务处理接口。
     */
    @Autowired
    public void setDevScreenLogService(DevScreenLogService devScreenLogService) {
        this.devScreenLogService = devScreenLogService;
    }

    /**
     * 大屏日志的业务处理接口。
     */
    private DevScreenLogService devScreenLogService;

}
