package cn.woyun.anov.gateway.management.service;

import cn.woyun.anov.gateway.management.bean.response.dev.DevScreenResponseBean;
import cn.woyun.anov.sdk.dev.entity.DevScreen;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 大屏业务处理的代理接口。
 *
 * @author xuepeng
 */
public interface DevScreenServiceProxy {

    /**
     * 创建大屏。
     *
     * @param devScreen 大屏对象。
     * @return 是否创建成功。
     */
    boolean create(final DevScreen devScreen);

    /**
     * 修改大屏。
     *
     * @param devScreen 大屏对象。
     * @return 是否创建成功。
     */
    boolean update(final DevScreen devScreen);

    /**
     * 保存大屏与功能的关系。
     *
     * @param id 大屏主键。
     */
    List<String> findScreenFunc(final long id);

    /**
     * 根据主键批量删除大屏。
     *
     * @param ids 大屏主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final List<Long> ids);

    /**
     * 根据条件分页查询大屏。
     *
     * @param page      分页信息。
     * @param devScreen 查询条件。
     * @return 大屏集合。
     */
    Page<DevScreen> findByPageAndCondition(final Page<DevScreen> page, final DevScreen devScreen);

    /**
     * 根据主键查询芯片。
     *
     * @param id 主键。
     * @return 芯片。
     */
    String findCoreById(final long id);

    /**
     * 根据标识查询大屏的截图。
     *
     * @param screenKey 大屏标识。
     * @return 大屏截图集合。
     */
    List<String> findDevScreenPicByKey(final String screenKey);

    /**
     * 设置部门名称。
     *
     * @param devScreens 大屏集合。
     */
    void setDeptName(final List<DevScreenResponseBean> devScreens);

}
