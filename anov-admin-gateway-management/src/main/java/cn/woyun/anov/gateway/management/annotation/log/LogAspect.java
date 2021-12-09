package cn.woyun.anov.gateway.management.annotation.log;

import cn.woyun.anov.bean.BeanUtil;
import cn.woyun.anov.gateway.management.bean.request.mgt.LogRequestBean;
import cn.woyun.anov.gateway.management.config.security.WebSecurityAuthenticationDetails;
import cn.woyun.anov.gateway.management.controller.BaseController;
import cn.woyun.anov.sdk.mgt.entity.SysLog;
import cn.woyun.anov.sdk.mgt.service.log.SysLogService;
import cn.woyun.anov.web.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 日志切面类。
 *
 * @author xuepeng
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    /**
     * 设置本次操作的日志。
     */
    @Pointcut("@annotation(cn.woyun.anov.gateway.management.annotation.log.OperationLog)")
    private void operation() {
        // 设置本次操作的日志
    }

    /**
     * 保存操作日志。
     *
     * @param joinPoint 连接点。
     */
    @Before("operation()")
    public void beforeOperationLog(final JoinPoint joinPoint) {
        if (joinPoint.getTarget() instanceof BaseController) {
            try {
                final LogRequestBean logRequestBean = createLog(joinPoint, null);
                sysLogService.create(BeanUtil.objToObj(logRequestBean, SysLog.class));
            } catch (Exception e) {
                log.error("save op log failed, cause is {}", e.getMessage());
            }
        }
    }

    /**
     * 保存错误日志。
     *
     * @param joinPoint 连接点。
     * @param e         错误信息。
     */
    @AfterThrowing(pointcut = "operation()", throwing = "e")
    public void throwingOperationLog(JoinPoint joinPoint, Throwable e) {
        if (joinPoint.getTarget() instanceof BaseController) {
            try {
                final LogRequestBean logRequestBean = createLog(joinPoint, e);
                sysLogService.create(BeanUtil.objToObj(logRequestBean, SysLog.class));
            } catch (Exception exception) {
                log.error("save err log failed, cause is {}", exception.getMessage());
            }
        }
    }

    /**
     * 创建日志的请求实体对象。
     *
     * @param joinPoint 连接点。
     * @param e         错误信息。
     * @return 日志的请求实体对象。
     */
    private LogRequestBean createLog(final JoinPoint joinPoint, final Throwable e) {
        final Object controller = joinPoint.getTarget();
        final HttpServletRequest request = ((BaseController) controller).getHttpServletRequest();
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final OperationLog operationLogAnnotation = method.getAnnotation(OperationLog.class);
        //  创建日志操作对象
        final LogRequestBean logRequestBean = new LogRequestBean();
        logRequestBean.setCreateUser(authentication.getName());
        logRequestBean.setCreateTime(LocalDateTime.now());
        logRequestBean.setTenantId(((WebSecurityAuthenticationDetails) authentication.getDetails()).getTenantId());
        logRequestBean.setLogSystem(operationLogAnnotation.system());
        logRequestBean.setLogModule(operationLogAnnotation.module());
        logRequestBean.setLogDescription(operationLogAnnotation.description());
        logRequestBean.setLogIp(WebUtil.getRealIpAddress(request));
        logRequestBean.setLogReqUrl(request.getRequestURI());
        logRequestBean.setLogReqBrowser(WebUtil.getBrowser(request));
        logRequestBean.setLogClassName(controller.getClass().getName());
        logRequestBean.setLogMethodName(method.getName());
        if (Objects.isNull(e)) {
            logRequestBean.setLogType(operationLogAnnotation.type().name());
            StringBuilder args = new StringBuilder();
            for (Object arg : joinPoint.getArgs()) {
                args.append("[").append(arg.toString()).append("],");
            }
            logRequestBean.setLogReqBody(args.toString());
        } else {
            logRequestBean.setLogType("ERROR");
            logRequestBean.setLogReqBody(e.getMessage());
        }
        return logRequestBean;
    }

    /**
     * 自动装配日志操作的业务处理接口。
     *
     * @param sysLogService 日志操作的业务处理接口。
     */
    @Autowired
    public void setSysLogService(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 日志操作的业务处理接口。
     */
    private SysLogService sysLogService;

}
