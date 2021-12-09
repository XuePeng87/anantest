package cn.woyun.anov.gateway.management.controller.dev;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.annotation.user.CreateUser;
import cn.woyun.anov.gateway.management.annotation.user.ModifyUser;
import cn.woyun.anov.gateway.management.bean.request.dev.DevScreenQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.request.dev.DevScreenRequestBean;
import cn.woyun.anov.gateway.management.bean.response.dev.DevScreenResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.gateway.management.service.DevScreenServiceProxy;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.page.PageResult;
import cn.woyun.anov.sdk.dev.entity.DevScreen;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 大屏的API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/devs")
@Api(tags = "大屏API接口")
public class DevScreenController extends BaseController {

    /**
     * 创建大屏。
     *
     * @param devScreenRequestBean 大屏对象。
     * @return 是否创建成功。
     */
    @PostMapping("/v1")
    @OperationLog(system = "管理平台", module = "大屏管理", description = "创建大屏", type = OperationTypeEnum.CREATE)
    @CreateUser
    @ApiOperation(value = "创建大屏")
    public HttpResult<Boolean> create(@Valid @RequestBody final DevScreenRequestBean devScreenRequestBean) {
        final DevScreen devScreen = BeanUtil.objToObj(devScreenRequestBean, DevScreen.class);
        if (devScreenServiceProxy.create(devScreen)) {
            return DefaultHttpResultFactory.success("创建大屏成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("创建大屏失败。", Boolean.FALSE);
    }

    /**
     * 修改大屏。
     *
     * @param devScreenRequestBean 大屏对象。
     * @return 是否创建成功。
     */
    @PutMapping("/v1/{id}")
    @OperationLog(system = "管理平台", module = "大屏管理", description = "修改大屏", type = OperationTypeEnum.UPDATE)
    @ModifyUser
    @ApiOperation(value = "修改大屏")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "大屏主键", required = true)
    })
    public HttpResult<Boolean> update(@Valid @NotNull(message = "大屏主键不能为空") @PathVariable(value = "id") final long id,
                                      @Valid @RequestBody final DevScreenRequestBean devScreenRequestBean) {
        final DevScreen devScreen = BeanUtil.objToObj(devScreenRequestBean, DevScreen.class);
        devScreen.setId(id);
        if (devScreenServiceProxy.update(devScreen)) {
            return DefaultHttpResultFactory.success("修改大屏成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("修改大屏失败。", Boolean.FALSE);
    }

    /**
     * 查询大屏的功能。
     *
     * @param id 大屏主键。
     * @return 是否设置成功。
     */
    @GetMapping("/v1/{id}/func")
    @OperationLog(system = "管理平台", module = "大屏管理", description = "查询大屏的功能", type = OperationTypeEnum.QUERY)
    @ApiOperation("查询大屏的功能")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "部门主键", required = true)
    })
    public HttpResult<List<String>> findScreenFunc(@Valid @NotNull(message = "大屏主键不能为空") @PathVariable(value = "id") final long id) {
        final List<String> funcs = devScreenServiceProxy.findScreenFunc(id);
        return DefaultHttpResultFactory.success("查询大屏的功能。", funcs);
    }

    /**
     * 删除大屏。
     *
     * @param ids 大屏主键集合。
     * @return 是否删除成功。
     */
    @DeleteMapping("/v1")
    @OperationLog(system = "管理平台", module = "大屏管理", description = "删除大屏", type = OperationTypeEnum.DELETE)
    @ApiOperation(value = "删除大屏")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "ids", value = "大屏主键集合", required = true)
    })
    public HttpResult<Boolean> delete(@Valid @NotEmpty(message = "大屏主键不能为空") @RequestBody final List<Long> ids) {
        if (devScreenServiceProxy.deleteByIds(ids)) {
            return DefaultHttpResultFactory.success("删除大屏成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("删除大屏失败。", Boolean.FALSE);
    }

    /**
     * 根据条件分页查询大屏。
     *
     * @param devScreenQueryRequestBean 大屏的查询请求参数类。
     * @return 用户集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "大屏管理", description = "根据条件分页查询大屏", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件分页查询大屏")
    public HttpResult<PageResult<DevScreenResponseBean>> findByConditionAndPage(@Valid @RequestBody final DevScreenQueryRequestBean devScreenQueryRequestBean) {
        final DevScreen devScreen = BeanUtil.objToObj(devScreenQueryRequestBean, DevScreen.class);
        final Page<DevScreen> page = pageParamToPage(devScreenQueryRequestBean.getPage());
        final Page<DevScreen> devScreens = devScreenServiceProxy.findByPageAndCondition(page, devScreen);
        final PageResult<DevScreenResponseBean> result = pageToPageResult(devScreens, DevScreenResponseBean.class);
        devScreenServiceProxy.setDeptName(result.getRecord());
        return DefaultHttpResultFactory.success("根据条件分页查询大屏成功。", result);
    }

    /**
     * 根据主键查询芯片。
     *
     * @param id 主键。
     * @return 芯片。
     */
    @GetMapping("/v1/{id}/core")
    @OperationLog(system = "管理平台", module = "大屏管理", description = "下载芯片", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "下载芯片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "大屏主键", required = true)
    })
    public HttpResult<String> findCoreById(@Valid @NotNull(message = "大屏主键不能为空") @PathVariable(value = "id") final long id) {
        final String result = devScreenServiceProxy.findCoreById(id);
        return DefaultHttpResultFactory.success("根据主键下载芯片。", result);
    }

    /**
     * 根据标识查询图片。
     *
     * @param key 大屏标识。
     * @return 大屏图片。
     */
    @GetMapping("/v1/{key}/pic")
    @OperationLog(system = "管理平台", module = "大屏管理", description = "查看图片", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查看图片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "key", value = "大屏标识", required = true)
    })
    public HttpResult<List<String>> findDevScreenPicByKey(@Valid @NotNull(message = "大屏标识不能为空")
                                                          @PathVariable(value = "key") final String key) {
        final List<String> result = devScreenServiceProxy.findDevScreenPicByKey(key);
        return DefaultHttpResultFactory.success("查询大屏图片成功。", result);
    }

    /**
     * 自动装配大屏的业务处理接口。
     *
     * @param devScreenServiceProxy 大屏的业务处理接口。
     */
    @Autowired
    public void setDevScreenServiceProxy(DevScreenServiceProxy devScreenServiceProxy) {
        this.devScreenServiceProxy = devScreenServiceProxy;
    }

    /**
     * 大屏的业务处理接口。
     */
    private DevScreenServiceProxy devScreenServiceProxy;

}
