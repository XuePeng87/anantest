package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreenPic;
import cn.woyun.anov.sdk.dev.mapper.DevScreenPicMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 大屏图片的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-17
 */
@Service
public class DevScreenPicServiceImpl extends ServiceImpl<DevScreenPicMapper, DevScreenPic> implements DevScreenPicService {

    /**
     * 保存大屏图片。
     *
     * @param devScreenPic 大屏图片对象。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveDevScreenPic(final DevScreenPic devScreenPic) {
        // 保存大屏图片
        final DevScreenPic pic = super.getOne(
                createQueryWrapper().lambda()
                        .eq(DevScreenPic::getScreenKey, devScreenPic.getScreenKey())
                        .eq(DevScreenPic::getMonitorImg, devScreenPic.getMonitorImg())
        );
        devScreenPic.setCreateTime(LocalDateTime.now());
        if (Objects.isNull(pic)) {
            final long tenantId = devScreenService.findByKey(devScreenPic.getScreenKey()).getTenantId();
            devScreenPic.setTenantId(tenantId);
            super.save(devScreenPic);
        } else {
            devScreenPic.setId(pic.getId());
            super.updateById(devScreenPic);
        }
        // 更新大屏的最新图片
        return devScreenService.updatePicByKey(devScreenPic.getScreenKey(), devScreenPic.getMonitorImg());
    }

    /**
     * 根据标识查询大屏的截图。
     *
     * @param screenKey 大屏标识。
     * @return 大屏截图集合。
     */
    @Override
    public List<String> findDevScreenPicByKey(final String screenKey) {
        final List<DevScreenPic> pics = super.list(
                createQueryWrapper().lambda().eq(DevScreenPic::getScreenKey, screenKey)
        );
        return pics.stream().map(DevScreenPic::getMonitorImg).collect(Collectors.toList());
    }

    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<DevScreenPic> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 自动装配大屏业务处理接口。
     *
     * @param devScreenService 大屏业务处理接口。
     */
    @Autowired
    public void setDevScreenService(DevScreenService devScreenService) {
        this.devScreenService = devScreenService;
    }

    /**
     * 大屏业务处理接口。
     */
    private DevScreenService devScreenService;

}