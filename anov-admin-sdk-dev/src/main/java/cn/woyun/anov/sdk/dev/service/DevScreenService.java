package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreen;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 大屏的业务处理接口。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-15
 */
public interface DevScreenService extends IService<DevScreen> {

    /**
     * 创建大屏。
     *
     * @param devScreen 大屏。
     * @return 是否创建成功。
     */
    boolean create(final DevScreen devScreen);

    /**
     * 修改大屏。
     *
     * @param devScreen 大屏。
     * @return 是否修改成功。
     */
    boolean update(final DevScreen devScreen);

    /**
     * 更新大屏的预览状态，并把大屏的检测次数+1
     *
     * @param devScreen 大屏。
     * @return 是否修改成功。
     */
    boolean updateScreenPriview(final DevScreen devScreen);

    /**
     * 根据唯一标识更新最新图片。
     *
     * @param key    唯一标识。
     * @param picUrl 图片地址。
     * @return 是否更新成功。
     */
    boolean updatePicByKey(final String key, final String picUrl);

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
     * 根据大屏唯一标识查询大屏。
     *
     * @param key 唯一标识。
     * @return 大屏。
     */
    DevScreen findByKey(final String key);

    /**
     * 根据主键查询大屏的功能。
     *
     * @param id 主键。
     * @return 大屏功能集合。
     */
    List<String> findFuncsById(final long id);

}