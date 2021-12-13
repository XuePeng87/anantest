package cn.woyun.anov.gateway.management.controller.mgt;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.annotation.user.CreateUser;
import cn.woyun.anov.gateway.management.annotation.user.ModifyUser;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.bean.request.mgt.RoleQueryRequestBean;
import cn.woyun.anov.gateway.management.bean.request.mgt.RoleRequestBean;
import cn.woyun.anov.gateway.management.bean.response.mgt.RoleResponseBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.page.PageResult;
import cn.woyun.anov.sdk.mgt.entity.SysRole;
import cn.woyun.anov.sdk.mgt.service.role.SysRoleService;
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
 * 角色的API类。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/roles")
@Api(tags = "角色API接口")
@SaCheckLogin
public class RoleController extends BaseController {

    // TODO 角色编号、名称不能重复

    /**
     * 创建角色。
     *
     * @param roleRequestBean 角色的请求参数类
     * @return 是否保存成功。
     */
    @PostMapping("/v1")
    @CreateUser
    @OperationLog(system = "管理平台", module = "角色管理", description = "创建角色", type = OperationTypeEnum.CREATE)
    @ApiOperation("创建角色")
    public HttpResult<Boolean> create(@Valid @RequestBody final RoleRequestBean roleRequestBean) {
        final SysRole sysRole = BeanUtil.objToObj(roleRequestBean, SysRole.class);
        if (sysRoleService.create(sysRole)) {
            return DefaultHttpResultFactory.success("创建角色成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("创建角色失败。", Boolean.FALSE);
    }

    /**
     * 修改角色。
     *
     * @param id              角色主键。
     * @param roleRequestBean 角色的请求参数类。
     * @return 是否修改成功。
     */
    @PutMapping("/v1/{id}")
    @ModifyUser
    @OperationLog(system = "管理平台", module = "角色管理", description = "修改角色", type = OperationTypeEnum.UPDATE)
    @ApiOperation("修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "角色主键", required = true)
    })
    public HttpResult<Boolean> update(@Valid @NotNull(message = "角色主键不能为空") @PathVariable(value = "id") final long id,
                                      @Valid @RequestBody final RoleRequestBean roleRequestBean) {
        final SysRole sysRole = BeanUtil.objToObj(roleRequestBean, SysRole.class);
        sysRole.setId(id);
        if (sysRoleService.update(sysRole)) {
            return DefaultHttpResultFactory.success("修改角色成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("修改角色失败。", Boolean.FALSE);
    }


    /**
     * 保存角色下的用户。
     *
     * @param id      角色主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    @PutMapping("/v1/{id}/user")
    @OperationLog(system = "管理平台", module = "角色管理", description = "保存角色下的用户", type = OperationTypeEnum.GRANT)
    @ApiOperation("保存角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "角色主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "userIds", value = "用户主键集合", required = true)
    })
    public HttpResult<Boolean> saveRoleUser(@Valid @NotNull(message = "角色主键不能为空") @PathVariable(value = "id") final long id,
                                            @Valid @NotEmpty(message = "用户主键不能为空") @RequestBody final List<Long> userIds) {
        sysRoleService.saveRoleToUser(id, userIds);
        return DefaultHttpResultFactory.success("设置角色下的用户成功。", Boolean.TRUE);
    }

    /**
     * 保存角色下的功能。
     *
     * @param id      角色主键。
     * @param funcIds 功能主键集合。
     * @return 是否保存成功。
     */
    @PutMapping("/v1/{id}/func")
    @OperationLog(system = "管理平台", module = "角色管理", description = "保存角色下的功能", type = OperationTypeEnum.GRANT)
    @ApiOperation("保存角色下的功能")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "角色主键", required = true),
            @ApiImplicitParam(paramType = "path", name = "funcIds", value = "功能主键集合", required = true)
    })
    public HttpResult<Boolean> saveRoleFunc(@Valid @NotNull(message = "角色主键不能为空") @PathVariable(value = "id") final long id,
                                            @Valid @NotEmpty(message = "功能主键不能为空") @RequestBody final List<Long> funcIds) {
        sysRoleService.saveRoleToFunc(id, funcIds);
        return DefaultHttpResultFactory.success("设置角色下的功能成功。", Boolean.TRUE);
    }

    /**
     * 根据主键批量删除角色。
     *
     * @param ids 角色主键。
     * @return 是否删除成功。
     */
    @DeleteMapping("/v1")
    @OperationLog(system = "管理平台", module = "角色管理", description = "删除角色", type = OperationTypeEnum.DELETE)
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "ids", value = "角色主键集合", required = true)
    })
    public HttpResult<Boolean> delete(@Valid @NotEmpty(message = "角色主键不能为空") @RequestBody final List<Long> ids) {
        if (sysRoleService.deleteByIds(ids)) {
            return DefaultHttpResultFactory.success("删除角色成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("删除角色失败。", Boolean.FALSE);
    }

    /**
     * 根据主键查询角色下的用户。
     *
     * @param id 角色主键。
     * @return 角色下的用户主键集合。
     */
    @GetMapping("/v1/{id}/user")
    @OperationLog(system = "管理平台", module = "角色管理", description = "查询角色下的用户", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "角色主键", required = true)
    })
    public HttpResult<List<Long>> findRoleUsersById(@Valid @NotNull(message = "角色主键不能为空") @PathVariable(value = "id") final long id) {
        final List<Long> userIds = sysRoleService.findRoleUsersById(id);
        return DefaultHttpResultFactory.success("查询角色下的用户。", userIds);
    }

    /**
     * 根据主键查询角色下的功能。
     *
     * @param id 角色主键。
     * @return 角色下的功能主键集合。
     */
    @GetMapping("/v1/{id}/func")
    @OperationLog(system = "管理平台", module = "角色管理", description = "查询角色下的功能", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询角色下的功能")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "角色主键", required = true)
    })
    public HttpResult<List<Long>> findRoleFuncsById(@Valid @NotNull(message = "角色主键不能为空") @PathVariable(value = "id") final long id) {
        final List<Long> funcIds = sysRoleService.findRoleFuncsById(id);
        return DefaultHttpResultFactory.success("查询角色下的功能。", funcIds);
    }

    /**
     * 根据条件分页查询角色。
     *
     * @param roleQueryRequestBean 角色的查询请求参数类
     * @return 角色集合。
     */
    @PostMapping("/v1/conditions")
    @OperationLog(system = "管理平台", module = "角色管理", description = "根据条件分页查询角色", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "根据条件分页查询角色")
    public HttpResult<PageResult<RoleResponseBean>> findByConditionAndPage(@Valid @RequestBody final RoleQueryRequestBean roleQueryRequestBean) {
        final SysRole sysRole = BeanUtil.objToObj(roleQueryRequestBean, SysRole.class);
        final Page<SysRole> page = pageParamToPage(roleQueryRequestBean.getPage());
        final Page<SysRole> result = sysRoleService.findByPageAndCondition(page, sysRole);
        return DefaultHttpResultFactory.success(
                "根据条件分页查询角色成功。",
                pageToPageResult(result, RoleResponseBean.class)
        );
    }

    /**
     * @return 查询全部的业务角色，不包含超级管理员和系统管理员的角色。
     */
    @GetMapping("/v1")
    @OperationLog(system = "管理平台", module = "角色管理", description = "查询全部角色", type = OperationTypeEnum.QUERY)
    @ApiOperation(value = "查询全部角色")
    public HttpResult<List<RoleResponseBean>> findAll() {
        final List<SysRole> sysRoles = sysRoleService.findBusinessRoles();
        return DefaultHttpResultFactory.success(
                "查询全部角色成功。",
                BeanUtil.listObjToListObj(sysRoles, RoleResponseBean.class)
        );
    }

    /**
     * 自动装配角色的业务处理接口。
     *
     * @param sysRoleService 角色的业务处理接口。
     */
    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 角色的业务处理接口。
     */
    private SysRoleService sysRoleService;

}
