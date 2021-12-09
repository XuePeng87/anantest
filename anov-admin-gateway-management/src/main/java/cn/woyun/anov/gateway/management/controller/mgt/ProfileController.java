package cn.woyun.anov.gateway.management.controller.mgt;

import cn.woyun.anov.gateway.management.annotation.log.OperationTypeEnum;
import cn.woyun.anov.gateway.management.annotation.user.ModifyUser;
import cn.woyun.anov.gateway.management.annotation.log.OperationLog;
import cn.woyun.anov.gateway.management.bean.request.mgt.UserPasswordRequestBean;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.http.DefaultHttpResultFactory;
import cn.woyun.anov.http.HttpResult;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 个人中心的API Controller。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/v1/profiles")
@Api(tags = "用户中心API接口")
public class ProfileController extends BaseController {

    /**
     * 修改个人密码。
     *
     * @param id                      用户主键。
     * @param userPasswordRequestBean 用户的修改密码请求参数类。
     * @return 是否修改成功。
     */
    @PutMapping("/v1/{id}/update-password")
    @ModifyUser
    @OperationLog(system = "管理平台", module = "个人中心", description = "修改个人密码", type = OperationTypeEnum.UPDATE)
    @ApiOperation(value = "修改个人密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "用户主键", required = true)
    })
    public HttpResult<Boolean> updatePassword(@Valid @NotNull(message = "用户主键不能为空") @PathVariable(value = "id") final long id,
                                              @Valid @RequestBody final UserPasswordRequestBean userPasswordRequestBean) {
        if (sysUserService.updatePassword(id,
                userPasswordRequestBean.getOldPassword(),
                userPasswordRequestBean.getNewPassword())) {
            return DefaultHttpResultFactory.success("修改密码成功。", Boolean.TRUE);
        }
        return DefaultHttpResultFactory.fail("修改密码失败。", Boolean.FALSE);
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
