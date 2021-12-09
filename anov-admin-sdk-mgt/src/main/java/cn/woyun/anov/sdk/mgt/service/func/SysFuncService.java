package cn.woyun.anov.sdk.mgt.service.func;

import cn.woyun.anov.sdk.mgt.entity.SysFunc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 功能的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysFuncService extends IService<SysFunc> {

    /**
     * 创建功能。
     *
     * @param sysFunc 功能实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysFunc sysFunc);

    /**
     * 修改功能。
     *
     * @param sysFunc 功能实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysFunc sysFunc);

    /**
     * 根据主键批量删除功能。
     *
     * @param ids 功能主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final Collection<Long> ids);

    /**
     * 检测功能名称是否存在。
     *
     * @param id   功能主键。
     * @param name 功能名称。
     * @return 是否存在。
     */
    boolean checkNameExisted(final long id, final String name);

    /**
     * 检测组件名称是否存在。
     *
     * @param id            功能主键。
     * @param componentName 组件名称。
     * @return 是否存在。
     */
    boolean checkComponentNameExisted(final long id, final String componentName);

    /**
     * 根据条件查询功能。
     *
     * @param sysFunc 查询条件。
     * @return 功能集合。
     */
    List<SysFunc> findByCondition(final SysFunc sysFunc);

    /**
     * 根据功能主键查询功能。
     *
     * @param ids 功能主键集合。
     * @return 功能集合。
     */
    List<SysFunc> findByIds(final List<Long> ids);

    /**
     * @return 查询全部功能。
     */
    List<SysFunc> findAll();

    /**
     * @return 查询全部可见的功能。
     */
    List<SysFunc> findVisible();

}