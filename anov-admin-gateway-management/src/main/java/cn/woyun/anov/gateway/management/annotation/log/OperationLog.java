package cn.woyun.anov.gateway.management.annotation.log;

import java.lang.annotation.*;

/**
 * 设置操作日志。
 *
 * @author xuepeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * @return 系统。
     */
    String system();

    /**
     * @return 模块。
     */
    String module();

    /**
     * @return 描述。
     */
    String description();

    /**
     * @return 操作日志类型。
     */
    OperationTypeEnum type();

}
