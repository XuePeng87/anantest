package cn.woyun.anov.gateway.management.controller.mgt;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.annotation.user.CreateUser;
import cn.woyun.anov.gateway.management.annotation.user.ModifyUser;
import cn.woyun.anov.gateway.management.bean.request.mgt.FuncQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.request.mgt.FuncRequestBean;
import cn.woyun.anov.gateway.management.bean.response.mgt.FuncResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.entity.SysFunc;
import cn.woyun.anov.sdk.mgt.service.func.SysFuncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 功能的API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/funcs")
@Api(tags = "功能API接口")
public class FuncController extends BaseController {

    /**
     * 创建功能。
     *
     * @param funcRequestBean 功能的请求参数类。
     * @return 是否创建成功。
     */
    @PostMapping("/v1")
    @OperationLog(system = "管理平台", module = "功能管理", description = "创建功能", type = OperationTypeEnum.CREATE)
    @CreateUser
    @ApiOperation(value = "创建功能")
    public HttpResult<Boolean> create(@Valid @RequestBody final FuncRequestBean funcRequestBean) {
        final SysFunc sysFunc = BeanUtil.objToObj(funcRequestBean, SysFunc.class);
        if (sysFuncService.create(sysFunc)) {
            return DefaultHttpResultFactory.success("创建功能成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("创建功能失败。", Boolean.FALSE);
    }

    /**
     * 修改功能。
     *
     * @param id              功能主键。
     * @param funcRequestBean 功能的请求参数类。
     * @return 是否修改成功。
     */
    @PutMapping("/v1/{id}")
    @OperationLog(system = "管理平台", module = "功能管理", description = "修改功能", type = OperationTypeEnum.UPDATE)
    @ModifyUser
    @ApiOperation(value = "修改功能")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "功能主键", required = true)
    })
    public HttpResult<Boolean> update(@Valid @NotNull(message = "功能主键不能为空") @PathVariable(value = "id") final long id,
                                      @Valid @RequestBody final FuncRequestBean funcRequestBean) {
        final SysFunc sysFunc = BeanUtil.objToObj(funcRequestBean, SysFunc.class);
        sysFunc.setId(id);
        if (sysFuncService.update(sysFunc)) {
            return DefaultHttpResultFactory.success("修改功能成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("修改功能失败。", Boolean.FALSE);
    }

    /**
     * 根据主键批量删除功能。
     *
     * @param ids 功能主键。
     * @return 是否删除成功。
     */
    @DeleteMapping("/v1")
    @OperationLog(system = "管理平台", module = "功能管理", description = "功能用户", type = OperationTypeEnum.DELETE)
    @ApiOperation(value = "删除功能")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "ids", value = "功能主键集合", required = true)
    })
    public HttpResult<Boolean> delete(@Valid @NotEmpty(message = "功能主键不能为空") @RequestBody final List<Long> ids) {
        if (sysFuncService.deleteByIds(ids)) {
            return DefaultHttpResultFactory.success("删除功能成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("删除功能失败。", Boolean.FALSE);
    }

    /**
     * 查询功能名称是否存在。
     *
     * @param id   功能主键。
     * @param name 功能名称。
     * @return 是否存在。
     */
    @GetMapping("/v1/{id}/exist/name/{name}")
    @OperationLog(system = "管理平台", module = "功能管理", description = "查询功能名称是否存在", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询功能名称是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "功能主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "name", value = "功能名称", required = true)
    })
    public HttpResult<Boolean> checkNameExisted(@Valid @NotNull(message = "功能主键不能为空") @PathVariable(value = "id") final long id,
                                                @Valid @NotBlank(message = "功能名称不能为空") @Length(max = 32, message = "功能名称长度不能大于32个字符")
                                                @PathVariable(value = "name") final String name) {
        final boolean result = sysFuncService.checkNameExisted(id, name);
        return DefaultHttpResultFactory.success("查询功能名称是否存在。", result);
    }

    /**
     * 查询功能组件是否存在。
     *
     * @param id   功能主键。
     * @param name 功能组件名称。
     * @return 是否存在。
     */
    @GetMapping("/v1/{id}/exist/component-name/{name}")
    @OperationLog(system = "管理平台", module = "功能管理", description = "查询功能组件名称是否存在", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询功能组件名称是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "功能主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "name", value = "功能组件名称", required = true)
    })
    public HttpResult<Boolean> checkCpmponentNameExisted(@Valid @NotNull(message = "功能主键不能为空") @PathVariable(value = "id") final long id,
                                                         @Valid @NotBlank(message = "功能组件名称不能为空") @Length(max = 32, message = "功能组件名称长度不能大于32个字符")
                                                         @PathVariable(value = "name") final String name) {
        final boolean result = sysFuncService.checkComponentNameExisted(id, name);
        return DefaultHttpResultFactory.success("查询功能名称是否存在。", result);
    }

    /**
     * 根据条件查询功能。
     *
     * @param funcQueryRequestBean 功能的查询请求参数类
     * @return 功能集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "功能管理", description = "根据条件查询功能", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件查询功能")
    public HttpResult<List<FuncResponseBean>> findByCondition(@Valid @RequestBody final FuncQueryRequestBean funcQueryRequestBean) {
        final SysFunc sysFunc = BeanUtil.objToObj(funcQueryRequestBean, SysFunc.class);
        final List<SysFunc> sysFuncs = sysFuncService.findByCondition(sysFunc);
        final List<FuncResponseBean> result = BeanUtil.listObjToListObj(sysFuncs, FuncResponseBean.class);
        return DefaultHttpResultFactory.success("根据条件查询功能。", result);
    }

    /**
     * @return 查询全部功能。
     */
    @GetMapping("/v1")
    @OperationLog(system = "管理平台", module = "功能管理", description = "查询全部功能", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询全部功能")
    public HttpResult<List<FuncResponseBean>> findAll() {
        final List<FuncResponseBean> result = BeanUtil.listObjToListObj(sysFuncService.findAll(), FuncResponseBean.class);
        return DefaultHttpResultFactory.success("查询全部功能。", result);
    }

    /**
     * @return 查询全部功能。
     */
    @GetMapping("/v1/visible")
    @OperationLog(system = "管理平台", module = "功能管理", description = "查询全部可见的功能", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询全部可见的功能")
    public HttpResult<List<FuncResponseBean>> findVisible() {
        final List<FuncResponseBean> result = BeanUtil.listObjToListObj(sysFuncService.findVisible(), FuncResponseBean.class);
        return DefaultHttpResultFactory.success("查询全部可见的功能。", result);
    }

    /**
     * 自动装配功能的业务处理接口。
     *
     * @param sysFuncService 功能的业务处理接口
     */
    @Autowired
    public void setSysFuncService(SysFuncService sysFuncService) {
        this.sysFuncService = sysFuncService;
    }

    /**
     * 功能的业务处理接口。
     */
    private SysFuncService sysFuncService;

}
