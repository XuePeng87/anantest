package cn.woyun.anov.gateway.management.service;

import cn.woyun.anov.gateway.management.bean.response.dev.DevScreenResponseBean;
import cn.woyun.anov.sdk.dev.entity.DevScreen;
import cn.woyun.anov.sdk.dev.service.DevScreenPicService;
import cn.woyun.anov.sdk.dev.service.DevScreenService;
import cn.woyun.anov.sdk.mgt.service.dept.SysDeptService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 大屏业务处理的代理实现类。
 *
 * @author xuepeng
 */
@Component
public class DevScreenServiceProxyImpl implements DevScreenServiceProxy {

    /**
     * 创建大屏。
     *
     * @param devScreen 大屏对象。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final DevScreen devScreen) {
        return devScreenService.create(devScreen);
    }

    /**
     * 修改大屏。
     *
     * @param devScreen 大屏对象。
     * @return 是否创建成功。
     */
    @Override
    public boolean update(final DevScreen devScreen) {
        return devScreenService.update(devScreen);
    }

    /**
     * 保存大屏与功能的关系。
     *
     * @param id 大屏主键。
     */
    @Override
    public List<String> findScreenFunc(final long id) {
        return devScreenService.findFuncsById(id);
    }

    /**
     * 根据主键批量删除大屏。
     *
     * @param ids 大屏主键集合。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByIds(final List<Long> ids) {
        return devScreenService.deleteByIds(ids);
    }

    /**
     * 根据条件分页查询大屏。
     *
     * @param page      分页信息。
     * @param devScreen 查询条件。
     * @return 大屏集合。
     */
    @Override
    public Page<DevScreen> findByPageAndCondition(final Page<DevScreen> page, final DevScreen devScreen) {
        return devScreenService.findByPageAndCondition(page, devScreen);
    }

    /**
     * 设置部门名称。
     *
     * @param devScreens 大屏集合。
     */
    @Override
    public void setDeptName(final List<DevScreenResponseBean> devScreens) {
        final Map<Long, String> deptMap = sysDeptService.findAllToMap();
        for (final DevScreenResponseBean devScreen : devScreens) {
            if (deptMap.containsKey(devScreen.getDeptId())) {
                devScreen.setDeptName(deptMap.get(devScreen.getDeptId()));
            }
        }
    }

    /**
     * 根据主键查询芯片。
     *
     * @param id 主键。
     * @return 芯片。
     */
    @Override
    public String findCoreById(final long id) {
        return devScreenService.findCoreById(id);
    }

    /**
     * 根据标识查询大屏的截图。
     *
     * @param screenKey 大屏标识。
     * @return 大屏截图集合。
     */
    @Override
    public List<String> findDevScreenPicByKey(final String screenKey) {
        return devScreenPicService.findDevScreenPicByKey(screenKey);
    }

    /**
     * 自动装配大屏的业务处理接口。
     *
     * @param devScreenService 大屏的业务处理接口。
     */
    @Autowired
    public void setDevScreenService(DevScreenService devScreenService) {
        this.devScreenService = devScreenService;
    }

    /**
     * 自动装配部门的业务处理接口。
     *
     * @param sysDeptService 部门的业务处理接口。
     */
    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    /**
     * 自动装配大屏监控图片业务处理接口。
     *
     * @param devScreenPicService 大屏监控图片业务处理接口。
     */
    @Autowired
    public void setDevScreenPicService(DevScreenPicService devScreenPicService) {
        this.devScreenPicService = devScreenPicService;
    }

    /**
     * 大屏的业务处理接口。
     */
    private DevScreenService devScreenService;

    /**
     * 部门的业务处理接口。
     */
    private SysDeptService sysDeptService;

    /**
     * 大屏监控图片业务处理接口。
     */
    private DevScreenPicService devScreenPicService;

}
