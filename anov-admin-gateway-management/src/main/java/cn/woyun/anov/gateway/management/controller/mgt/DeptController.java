package cn.woyun.anov.gateway.management.controller.mgt;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.annotation.user.CreateUser;
import cn.woyun.anov.gateway.management.annotation.user.ModifyUser;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.bean.request.mgt.DeptQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.request.mgt.DeptRequestBean;
import cn.woyun.anov.gateway.management.bean.response.mgt.DeptResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.entity.SysDept;
import cn.woyun.anov.sdk.mgt.service.dept.SysDeptService;
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
 * 部门的API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/depts")
@Api(tags = "部门API接口")
public class DeptController extends BaseController {

    /**
     * 创建部门。
     *
     * @param deptRequestBean 部门对象。
     * @return 是否创建成功。
     */
    @PostMapping("/v1")
    @OperationLog(system = "管理平台", module = "部门管理", description = "创建部门", type = OperationTypeEnum.CREATE)
    @CreateUser
    @ApiOperation(value = "创建部门")
    public HttpResult<Boolean> create(@Valid @RequestBody final DeptRequestBean deptRequestBean) {
        final SysDept sysDept = BeanUtil.objToObj(deptRequestBean, SysDept.class);
        if (sysDeptService.create(sysDept)) {
            return DefaultHttpResultFactory.success("创建部门成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("创建部门失败。", Boolean.FALSE);
    }

    /**
     * 修改部门。
     *
     * @param id              部门主键。
     * @param deptRequestBean 部门对象。
     * @return 是否修改成功。
     */
    @PutMapping("/v1/{id}")
    @OperationLog(system = "管理平台", module = "部门管理", description = "修改部门", type = OperationTypeEnum.UPDATE)
    @ModifyUser
    @ApiOperation(value = "修改部门")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "部门主键", required = true)
    })
    public HttpResult<Boolean> update(@Valid @NotNull(message = "部门主键不能为空") @PathVariable(value = "id") final long id,
                                      @Valid @RequestBody final DeptRequestBean deptRequestBean) {
        final SysDept sysDept = BeanUtil.objToObj(deptRequestBean, SysDept.class);
        sysDept.setId(id);
        if (sysDeptService.update(sysDept)) {
            return DefaultHttpResultFactory.success("修改部门成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("修改部门失败。", Boolean.FALSE);
    }

    /**
     * 删除部门。
     *
     * @param ids 部门主键集合。
     * @return 是否删除成功。
     */
    @DeleteMapping("/v1")
    @OperationLog(system = "管理平台", module = "部门管理", description = "删除部门", type = OperationTypeEnum.DELETE)
    @ApiOperation(value = "删除部门")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "ids", value = "部门主键集合", required = true)
    })
    public HttpResult<Boolean> delete(@Valid @NotEmpty(message = "部门主键不能为空") @RequestBody final List<Long> ids) {
        if (sysDeptService.deleteByIds(ids)) {
            return DefaultHttpResultFactory.success("删除部门成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("删除部门失败。", Boolean.FALSE);
    }

    /**
     * 根据条件查询部门。
     *
     * @param deptQueryRequestBean 查询条件。
     * @return 部门集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "部门管理", description = "根据条件查询部门", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件查询部门")
    public HttpResult<List<DeptResponseBean>> findByCondition(@Valid @RequestBody final DeptQueryRequestBean deptQueryRequestBean) {
        final SysDept sysDept = BeanUtil.objToObj(deptQueryRequestBean, SysDept.class);
        final List<SysDept> sysDepts = sysDeptService.findByCondition(sysDept);
        final List<DeptResponseBean> result = BeanUtil.listObjToListObj(sysDepts, DeptResponseBean.class);
        return DefaultHttpResultFactory.success("根据条件查询部门。", result);
    }

    /**
     * @return 查询全部部门。
     */
    @GetMapping("/v1")
    @OperationLog(system = "管理平台", module = "部门管理", description = "查询全部部门", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询全部部门")
    public HttpResult<List<DeptResponseBean>> findAll() {
        final List<DeptResponseBean> result = BeanUtil.listObjToListObj(sysDeptService.findAll(), DeptResponseBean.class);
        return DefaultHttpResultFactory.success("查询全部部门。", result);
    }

    /**
     * 自动装配部门的业务处理接口。
     *
     * @param sysDeptService 部门的业务处理接口。
     */
    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    /**
     * 部门的业务处理接口。
     */
    private SysDeptService sysDeptService;

}
