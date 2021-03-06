package cn.woyun.anov.gateway.management.annotation.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.woyun.anov.gateway.management.bean.request.BaseRequestBean;
import cn.woyun.anov.sdk.mgt.entity.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 用户切面类。
 *
 * @author xuepeng
 */
@Component
@Aspect
public class UserAspect {

    /**
     * 设置本次操作的创建人
     */
    @Pointcut("@annotation(cn.woyun.anov.gateway.management.annotation.user.CreateUser)")
    private void createUser() {
        // 创建之前设置创建人
    }

    /**
     * 设置本次操作的创建人。
     *
     * @param joinPoint 连接点。
     */
    @Before("createUser()")
    public void beforeCreate(final JoinPoint joinPoint) {
        for (Object requestBean : joinPoint.getArgs()) {
            if (requestBean instanceof BaseRequestBean) {
                // 获取当前登录人
                final SysUser sysUser = (SysUser) StpUtil.getSession().get("user");
                final BaseRequestBean gatewayBaseRequestBean = (BaseRequestBean) requestBean;
                gatewayBaseRequestBean.setCreateUser(sysUser.getUserAccount());
                gatewayBaseRequestBean.setModifyUser(sysUser.getUserAccount());
            }
        }
    }

    /**
     * 设置本次操作的修改人。
     */
    @Pointcut("@annotation(cn.woyun.anov.gateway.management.annotation.user.ModifyUser)")
    private void modifyUser() {
        // 修改之前设置修改人
    }

    /**
     * 设置本次操作的创建人。
     *
     * @param joinPoint 连接点。
     */
    @Before("modifyUser()")
    public void beforeModify(JoinPoint joinPoint) {
        for (Object requestBean : joinPoint.getArgs()) {
            if (requestBean instanceof BaseRequestBean) {
                final SysUser sysUser = (SysUser) StpUtil.getSession().get("user");
                final BaseRequestBean gatewayBaseRequestBean = (BaseRequestBean) requestBean;
                gatewayBaseRequestBean.setModifyUser(sysUser.getUserAccount());
            }
        }
    }

}
