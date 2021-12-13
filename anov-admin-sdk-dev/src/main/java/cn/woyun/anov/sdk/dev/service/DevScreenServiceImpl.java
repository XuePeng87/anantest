package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.config.dev.DevScreenConfiguration;
import cn.woyun.anov.random.RandomUtil;
import cn.woyun.anov.sdk.dev.entity.DevScreen;
import cn.woyun.anov.sdk.dev.enums.ScreenStatus;
import cn.woyun.anov.sdk.dev.exception.DevScreenCannotCreateException;
import cn.woyun.anov.sdk.dev.mapper.DevScreenMapper;
import cn.woyun.anov.sdk.dev.service.core.ScreenCoreGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 大屏的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-15
 */
@Service
public class DevScreenServiceImpl extends ServiceImpl<DevScreenMapper, DevScreen> implements DevScreenService {

    /**
     * 创建大屏。
     *
     * @param devScreen 大屏。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final DevScreen devScreen) {
        if (devScreen.getDeptId() == 0L) {
            throw new DevScreenCannotCreateException("创建大屏的用户必须在某个部门下。");
        }
        // 生成大屏的Key
        String screenKey;
        do {
            screenKey = RandomUtil.get12String();
        } while (this.checkScreenKeyExist(screenKey));
        devScreen.setScreenKey(screenKey);
        // 设置大屏的过期时间
        devScreen.setScreenExpireTime(LocalDateTime.now().plusDays(
                devScreenConfiguration.getExpiration().toDays()
        ));
        devScreen.setScreenStatus(ScreenStatus.ENABLE.ordinal());
        devScreen.setScreenAuthenticated(Boolean.FALSE);
        devScreen.setScreenPreviewed(Boolean.FALSE);
        devScreen.setCreateTime(LocalDateTime.now());
        devScreen.setModifyTime(LocalDateTime.now());
        // 生成芯片
        final String param = screenCoreGenerator.generate(devScreen);
        devScreen.setScreenCore(param.getBytes(StandardCharsets.UTF_8));
        return super.save(devScreen);
    }

    /**
     * 修改大屏。
     *
     * @param devScreen 大屏。
     * @return 是否修改成功。
     */
    @Override
    @Transactional
    public boolean update(final DevScreen devScreen) {
        devScreen.setModifyTime(LocalDateTime.now());
        // 生成芯片
        final String param = screenCoreGenerator.generate(devScreen);
        devScreen.setScreenCore(param.getBytes(StandardCharsets.UTF_8));
        devScreenFuncRelationService.saveScreenFunc(
                devScreen.getId(),
                devScreen.getTenantId(),
                devScreen.getFuncs()
        );
        return super.updateById(devScreen);
    }

    /**
     * 更新大屏的预览状态，并把大屏的检测次数+1。
     *
     * @param devScreen 大屏。
     * @return 是否修改成功。
     */
    public boolean updateScreenPriview(final DevScreen devScreen) {
        // 把大屏的检测次数+1
        return super.update(devScreen,
                createUpdateWrapper().lambda()
                        .eq(DevScreen::getScreenKey, devScreen.getScreenKey())
                        .setSql("screen_check_count = screen_check_count + " + 1)
        );
    }

    /**
     * 根据唯一标识更新最新图片。
     *
     * @param key    唯一标识。
     * @param picUrl 图片地址。
     * @return 是否更新成功。
     */
    @Override
    public boolean updatePicByKey(final String key, final String picUrl) {
        final DevScreen devScreen = new DevScreen();
        devScreen.setScreenMonitorPic(picUrl);
        return super.update(devScreen,
                createUpdateWrapper().lambda().eq(DevScreen::getScreenKey, key)
        );
    }

    /**
     * 根据主键批量删除大屏。
     *
     * @param ids 大屏主键集合。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteByIds(final List<Long> ids) {
        // 删除大屏与功能的关系
        for (final long id : ids) {
            devScreenFuncRelationService.saveScreenFunc(id, null, new ArrayList<>(0));
        }
        // 删除大屏
        return super.removeByIds(ids);
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
        final QueryWrapper<DevScreen> queryWrapper = createQueryWrapper(devScreen);
        queryWrapper.select(DevScreen.class, columns -> !columns.getColumn().equals("screen_core"));
        return super.page(page, queryWrapper);
    }

    /**
     * 根据主键查询芯片。
     *
     * @param id 主键。
     * @return 芯片。
     */
    @Override
    public String findCoreById(final long id) {
        final DevScreen devScreen = super.getById(id);
        if (Objects.isNull(devScreen)) {
            return StringUtils.EMPTY;
        }
        final byte[] screenCore = devScreen.getScreenCore();
        return new String(screenCore);
    }

    /**
     * 根据大屏唯一标识查询大屏。
     *
     * @param key 唯一标识。
     * @return 大屏。
     */
    @Override
    public DevScreen findByKey(final String key) {
        final QueryWrapper<DevScreen> wrapper = createQueryWrapper();
        wrapper.lambda().eq(DevScreen::getScreenKey, key);
        final DevScreen devScreen = super.getOne(wrapper);
        if (Objects.isNull(devScreen)) {
            return null;
        }
        // 查询大屏的功能
        final List<String> funcs = devScreenFuncRelationService.findScreenFunc(devScreen.getId());
        devScreen.setFuncs(funcs);
        return devScreen;
    }

    /**
     * 根据主键查询大屏的功能。
     *
     * @param id 主键。
     * @return 大屏功能集合。
     */
    @Override
    public List<String> findFuncsById(final long id) {
        return devScreenFuncRelationService.findScreenFunc(id);
    }

    /**
     * 检测大屏key是否存在。
     *
     * @param screenKey 大屏Key。
     * @return 是否存在。
     */
    private boolean checkScreenKeyExist(final String screenKey) {
        return super.count(
                createQueryWrapper().lambda().eq(DevScreen::getScreenKey, screenKey)
        ) > 0;
    }

    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<DevScreen> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 根据条件创建QueryWrapper对象。
     *
     * @param devScreen 大屏对象。
     * @return QueryWrapper对象。
     */
    private QueryWrapper<DevScreen> createQueryWrapper(final DevScreen devScreen) {
        final QueryWrapper<DevScreen> wrapper = createQueryWrapper();
        final LambdaQueryWrapper<DevScreen> criteria = wrapper.lambda();
        if (StringUtils.isNotBlank(devScreen.getScreenName())) {
            criteria.like(DevScreen::getScreenName, devScreen.getScreenName());
        }
        if (!Objects.isNull(devScreen.getScreenStatus())) {
            criteria.eq(DevScreen::getScreenStatus, devScreen.getScreenStatus());
        }
        return wrapper;
    }

    /**
     * @return 创建UpdateWrapper对象。
     */
    private UpdateWrapper<DevScreen> createUpdateWrapper() {
        return new UpdateWrapper<>();
    }

    /**
     * 自动装配大屏芯片生成器。
     *
     * @param screenCoreGenerator 大屏芯片生成器。
     */
    @Autowired
    public void setScreenCoreGenerator(ScreenCoreGenerator screenCoreGenerator) {
        this.screenCoreGenerator = screenCoreGenerator;
    }

    /**
     * 自动装配大屏与功能管理的业务处理接口。
     *
     * @param devScreenFuncRelationService 大屏与功能管理的业务处理接口。
     */
    @Autowired
    public void setDevScreenFuncRelationService(DevScreenFuncRelationService devScreenFuncRelationService) {
        this.devScreenFuncRelationService = devScreenFuncRelationService;
    }

    /**
     * 自动装配大屏管理的配置信息。
     *
     * @param devScreenConfiguration 大屏管理的配置信息。
     */
    @Autowired
    public void setDevScreenConfiguration(DevScreenConfiguration devScreenConfiguration) {
        this.devScreenConfiguration = devScreenConfiguration;
    }

    /**
     * 大屏芯片生成器。
     */
    private ScreenCoreGenerator screenCoreGenerator;

    /**
     * 大屏与功能管理的业务处理接口。
     */
    private DevScreenFuncRelationService devScreenFuncRelationService;

    /**
     * 大屏管理的配置信息。
     */
    private DevScreenConfiguration devScreenConfiguration;

}