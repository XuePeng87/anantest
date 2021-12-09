package cn.woyun.anov.gateway.management.controller.system;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.bean.request.mgt.TenantQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.request.mgt.TenantRequestBean;
import cn.woyun.anov.gateway.management.bean.request.mgt.TenantSignupRequestBean;
import cn.woyun.anov.gateway.management.bean.response.mgt.TenantResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.page.PageResult;
import cn.woyun.anov.sdk.mgt.entity.SysTenant;
import cn.woyun.anov.sdk.mgt.service.tenant.SysTenantService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * 租户的API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/tenants")
@Api(tags = "租户API接口")
public class TenantController extends BaseController {

    /**
     * 注册租户。
     *
     * @param tenantSignupRequestBean 租户的请求对象。
     * @return 是否创建成功。
     */
    @PostMapping("/v1/signup")
    @OperationLog(system = "管理平台", module = "租户管理", description = "注册租户", type = OperationTypeEnum.CREATE)
    @ApiOperation(value = "租户注册")
    public HttpResult<Boolean> signup(@Valid @RequestBody final TenantSignupRequestBean tenantSignupRequestBean) {
        final SysTenant sysTenant = BeanUtil.objToObj(tenantSignupRequestBean, SysTenant.class);
        if (sysTenantService.create(sysTenant)) {
            return DefaultHttpResultFactory.success("注册租户成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("注册租户失败。", Boolean.FALSE);
    }

    /**
     * 创建租户。
     *
     * @param tenantRequestBean 租户的请求对象。
     * @return 是否创建成功。
     */
    @PostMapping("/v1")
    @OperationLog(system = "管理平台", module = "租户管理", description = "创建租户", type = OperationTypeEnum.CREATE)
    @ApiOperation(value = "创建租户")
    public HttpResult<Boolean> create(@Valid @RequestBody final TenantRequestBean tenantRequestBean) {
        final SysTenant sysTenant = BeanUtil.objToObj(tenantRequestBean, SysTenant.class);
        if (sysTenantService.create(sysTenant)) {
            return DefaultHttpResultFactory.success("创建租户成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("创建租户失败。", Boolean.FALSE);
    }

    /**
     * 修改租户。
     *
     * @param tenantRequestBean 租户的请求对象。
     * @return 是否修改成功。
     */
    @PutMapping("/v1/{id}")
    @OperationLog(system = "管理平台", module = "租户管理", description = "修改租户", type = OperationTypeEnum.UPDATE)
    @ApiOperation(value = "修改租户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "租户主键", required = true)
    })
    public HttpResult<Boolean> update(@Valid @NotNull(message = "租户主键不能为空") @PathVariable(value = "id") final long id,
                                      @Valid @RequestBody final TenantRequestBean tenantRequestBean) {
        final SysTenant sysTenant = BeanUtil.objToObj(tenantRequestBean, SysTenant.class);
        sysTenant.setId(id);
        if (sysTenantService.update(sysTenant)) {
            return DefaultHttpResultFactory.success("修改租户成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("修改租户失败。", Boolean.FALSE);
    }

    /**
     * 根据主键批量删除租户。
     *
     * @param ids 租户主键。
     * @return 是否删除成功。
     */
    @DeleteMapping("/v1")
    @OperationLog(system = "管理平台", module = "租户管理", description = "删除租户", type = OperationTypeEnum.DELETE)
    @ApiOperation(value = "删除租户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "ids", value = "用户主键集合", required = true)
    })
    public HttpResult<Boolean> delete(@Valid @NotEmpty(message = "租户主键不能为空") @RequestBody final List<Long> ids) {
        if (sysTenantService.deleteByIds(ids)) {
            return DefaultHttpResultFactory.success("删除租户成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("删除租户失败。", Boolean.FALSE);
    }

    /**
     * 根据条件分页查询租户。
     *
     * @param tenantQueryRequestBean 租户的查询请求参数类
     * @return 租户集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "租户管理", description = "根据条件分页查询租户", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件分页查询租户")
    public HttpResult<PageResult<TenantResponseBean>> findByConditionAndPage(@Valid @RequestBody final TenantQueryRequestBean tenantQueryRequestBean) {
        final SysTenant sysTenant = BeanUtil.objToObj(tenantQueryRequestBean, SysTenant.class);
        final Page<SysTenant> page = pageParamToPage(tenantQueryRequestBean.getPage());
        final Page<SysTenant> result = sysTenantService.findByPageAndCondition(page, sysTenant);
        return DefaultHttpResultFactory.success(
                "根据条件分页查询租户成功。",
                pageToPageResult(result, TenantResponseBean.class)
        );
    }

    /**
     * 查询租户电话号是否存在。
     *
     * @param id    租户主键。
     * @param phone 租户电话号。
     * @return 是否存在。
     */
    @GetMapping("/v1/{id}/exist/phone/{phone}")
    @OperationLog(system = "管理平台", module = "租户管理", description = "查询租户电话号是否存在", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询租户电话号是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "租户主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "phone", value = "租户手机号", required = true)
    })
    public HttpResult<Boolean> checkPhoneExisted(@Valid @NotNull(message = "租户主键不能为空") @PathVariable(value = "id") final long id,
                                                 @Valid @NotBlank(message = "租户电话号不能为空") @Length(max = 32, message = "租户手机号长度不能大于32个字符")
                                                 @PathVariable(value = "phone") final String phone) {
        final boolean result = sysTenantService.checkPhoneExisted(id, phone);
        return DefaultHttpResultFactory.success("查询租户手机号是否存在。", result);
    }

    /**
     * 查询租户邮箱是否存在。
     *
     * @param id    租户主键。
     * @param email 租户邮箱。
     * @return 是否存在。
     */
    @GetMapping("/v1/{id}/exist/email/{email}")
    @OperationLog(system = "管理平台", module = "租户管理", description = "查询租户邮箱是否存在", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询租户邮箱的是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "租户主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "email", value = "租户邮箱", required = true)
    })
    public HttpResult<Boolean> checkEmailExisted(@Valid @NotNull(message = "租户主键不能为空") @PathVariable(value = "id") final long id,
                                                 @Valid @NotBlank(message = "租户邮箱不能为空") @Length(max = 128, message = "租户邮箱长度不能大于128个字符")
                                                 @PathVariable(value = "email") final String email) {
        final boolean result = sysTenantService.checkEmailExisted(id, email);
        return DefaultHttpResultFactory.success("查询租户邮箱是否存在。", result);
    }

    /**
     * 自动装配租户的业务处理接口。
     *
     * @param sysTenantService 租户的业务处理接口。
     */
    @Autowired
    public void setSysTenantService(SysTenantService sysTenantService) {
        this.sysTenantService = sysTenantService;
    }

    /**
     * 租户的业务处理接口。
     */
    private SysTenantService sysTenantService;

}