package cn.woyun.anov.gateway.management.controller.mgt;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.annotation.user.CreateUser;
import cn.woyun.anov.gateway.management.annotation.user.ModifyUser;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.bean.request.mgt.UserQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.request.mgt.UserRequestBean;
import cn.woyun.anov.gateway.management.bean.response.mgt.UserResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.page.PageResult;
import cn.woyun.anov.sdk.mgt.entity.SysUser;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
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
import java.util.Objects;

/**
 * 用户的API Controller。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/users")
@Api(tags = "用户API接口")
@SaCheckLogin
public class UserController extends BaseController {

    /**
     * 创建用户。
     *
     * @param userRequestBean 用户的请求对象。
     * @return 是否创建成功。
     */
    @PostMapping("/v1")
    @CreateUser
    @OperationLog(system = "管理平台", module = "用户管理", description = "创建用户", type = OperationTypeEnum.CREATE)
    @ApiOperation(value = "创建用户")
    public HttpResult<Boolean> create(@Valid @RequestBody final UserRequestBean userRequestBean) {
        final SysUser sysUser = BeanUtil.objToObj(userRequestBean, SysUser.class);
        // 设置租户主键
        final Long tenantId = getTenantId();
        if (!Objects.isNull(tenantId)) sysUser.setTenantId(tenantId);
        sysUser.setTenantId(((SysUser) StpUtil.getSession().get("user")).getTenantId());
        if (sysUserService.create(sysUser)) {
            return DefaultHttpResultFactory.success("创建用户成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("创建用户失败。", Boolean.FALSE);
    }

    /**
     * 修改用户。
     *
     * @param id              用户主键。
     * @param userRequestBean 用户的请求对象。
     * @return 是否修改成功。
     */
    @PutMapping("/v1/{id}")
    @ModifyUser
    @OperationLog(system = "管理平台", module = "用户管理", description = "修改用户", type = OperationTypeEnum.UPDATE)
    @ApiOperation(value = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户主键", required = true)
    })
    public HttpResult<Boolean> update(@Valid @NotNull(message = "用户主键不能为空") @PathVariable(value = "id") final long id,
                                      @Valid @RequestBody final UserRequestBean userRequestBean) {
        final SysUser user = BeanUtil.objToObj(userRequestBean, SysUser.class);
        user.setId(id);
        if (sysUserService.update(user)) {
            return DefaultHttpResultFactory.success("修改用户成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("修改用户失败。", Boolean.FALSE);
    }

    /**
     * 重置密码。
     *
     * @param id 用户主键。
     * @return 是否重置成功。
     */
    @PutMapping("/v1/{id}/reset-password")
    @ModifyUser
    @OperationLog(system = "管理平台", module = "用户管理", description = "重置密码", type = OperationTypeEnum.UPDATE)
    @ApiOperation(value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户主键", required = true)
    })
    public HttpResult<Boolean> resetPassword(@Valid @NotNull(message = "用户主键不能为空") @PathVariable(value = "id") final long id) {
        if (sysUserService.resetPassword(id)) {
            return DefaultHttpResultFactory.success("重置密码成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("重置密码失败。", Boolean.FALSE);
    }

    /**
     * 根据主键批量删除用户。
     *
     * @param ids 用户主键。
     * @return 是否删除成功。
     */
    @DeleteMapping("/v1")
    @OperationLog(system = "管理平台", module = "用户管理", description = "删除用户", type = OperationTypeEnum.DELETE)
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "ids", value = "用户主键集合", required = true)
    })
    public HttpResult<Boolean> delete(@Valid @NotEmpty(message = "用户主键不能为空") @RequestBody final List<Long> ids) {
        if (sysUserService.deleteByIds(ids)) {
            return DefaultHttpResultFactory.success("删除用户成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("删除用户失败。", Boolean.FALSE);
    }

    /**
     * 查询用户帐号是否存在。
     *
     * @param id      用户主键。
     * @param account 用户帐号。
     * @return 是否存在。
     */
    @GetMapping("/v1/{id}/exist/account/{account}")
    @OperationLog(system = "管理平台", module = "用户管理", description = "查询用户帐号是否存在", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询用户帐号是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "account", value = "用户帐号", required = true)
    })
    public HttpResult<Boolean> checkAccountExisted(@Valid @NotNull(message = "用户主键不能为空") @PathVariable(value = "id") final long id,
                                                   @Valid @NotBlank(message = "用户帐号不能为空") @Length(max = 32, message = "用户帐号长度不能大于32个字符")
                                                   @PathVariable(value = "account") final String account) {
        final boolean result = sysUserService.checkAccountExisted(id, account);
        return DefaultHttpResultFactory.success("查询用户帐号是否存在。", result);
    }

    /**
     * 查询用户手机号是否存在。
     *
     * @param id    用户主键。
     * @param phone 用户手机号。
     * @return 是否存在。
     */
    @GetMapping("/v1/{id}/exist/phone/{phone}")
    @OperationLog(system = "管理平台", module = "用户管理", description = "查询用户手机号是否存在", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询用户手机号是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "phone", value = "用户帐号", required = true)
    })
    public HttpResult<Boolean> checkPhoneExisted(@Valid @NotNull(message = "用户主键不能为空") @PathVariable(value = "id") final long id,
                                                 @Valid @NotBlank(message = "用户手机号不能为空") @Length(max = 32, message = "用户手机号长度不能大于32个字符")
                                                 @PathVariable(value = "phone") final String phone) {
        final boolean result = sysUserService.checkPhoneExisted(id, phone);
        return DefaultHttpResultFactory.success("查询用户手机号是否存在。", result);
    }

    /**
     * 查询用户邮箱是否存在。
     *
     * @param id    用户主键。
     * @param email 用户邮箱。
     * @return 是否存在。
     */
    @GetMapping("/v1/{id}/exist/email/{email}")
    @OperationLog(system = "管理平台", module = "用户管理", description = "查询用户邮箱是否存在", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询用户邮箱是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "email", value = "用户邮箱", required = true)
    })
    public HttpResult<Boolean> checkEmailExisted(@Valid @NotNull(message = "用户主键不能为空") @PathVariable(value = "id") final long id,
                                                 @Valid @NotBlank(message = "用户邮箱不能为空") @Length(max = 128, message = "用户邮箱长度不能大于128个字符")
                                                 @PathVariable(value = "email") final String email) {
        final boolean result = sysUserService.checkEmailExisted(id, email);
        return DefaultHttpResultFactory.success("查询用户邮箱是否存在。", result);
    }

    /**
     * 根据条件分页查询用户。
     *
     * @param userQueryRequestBean 用户的查询请求参数类
     * @return 用户集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "用户管理", description = "根据条件分页查询用户", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件分页查询用户")
    public HttpResult<PageResult<UserResponseBean>> findByConditionAndPage(@Valid @RequestBody final UserQueryRequestBean userQueryRequestBean) {
        final SysUser sysUser = BeanUtil.objToObj(userQueryRequestBean, SysUser.class);
        final Page<SysUser> page = pageParamToPage(userQueryRequestBean.getPage());
        final Page<SysUser> result = sysUserService.findByPageAndCondition(page, sysUser);
        return DefaultHttpResultFactory.success(
                "根据条件分页查询用户成功。",
                pageToPageResult(result, UserResponseBean.class)
        );
    }

    /**
     * 自动装配用户的业务处理接口。
     *
     * @param sysUserService 用户的业务处理接口。
     */
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 用户的业务处理接口。
     */
    private SysUserService sysUserService;

}
