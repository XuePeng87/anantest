package cn.woyun.anov.sdk.mgt.service.dept;

import cn.woyun.anov.sdk.mgt.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 创建部门。
     *
     * @param sysDept 部门对象。
     * @return 是否创建成功。
     */
    boolean create(final SysDept sysDept);

    /**
     * 修改部门。
     *
     * @param sysDept 部门对象。
     * @return 是否修改成功。
     */
    boolean update(final SysDept sysDept);

    /**
     * 根据主键批量删除部门。
     * 当部门下有用户时，不可删除部门。
     *
     * @param ids 部门主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final Collection<Long> ids);

    /**
     * 根据条件查询部门。
     *
     * @param sysDept 查询条件。
     * @return 部门集合。
     */
    List<SysDept> findByCondition(final SysDept sysDept);

    /**
     * 根据主键查询部门。
     *
     * @param id 主键。
     * @return 部门。
     */
    SysDept findById(final long id);

    /**
     * @return 查询主键和名称的Map。
     */
    Map<Long, String> findAllToMap();

    /**
     * @return 查询全部部门。
     */
    List<SysDept> findAll();

}