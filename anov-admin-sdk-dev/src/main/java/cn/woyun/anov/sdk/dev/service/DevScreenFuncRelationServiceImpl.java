package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreenFuncRelation;
import cn.woyun.anov.sdk.dev.mapper.DevScreenFuncRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 大屏与功能管理的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-17
 */
@Service
public class DevScreenFuncRelationServiceImpl extends ServiceImpl<DevScreenFuncRelationMapper, DevScreenFuncRelation> implements DevScreenFuncRelationService {

    /**
     * 保存大屏与功能的关系。
     *
     * @param id        大屏主键。
     * @param tenantId  大屏租户主键。
     * @param funcNames 功能名称。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveScreenFunc(final long id, final Long tenantId, final Collection<String> funcNames) {
        // 删除大屏与功能的关系
        final UpdateWrapper<DevScreenFuncRelation> wrapper = createUpdateWrapper();
        wrapper.lambda().eq(DevScreenFuncRelation::getScreenId, id);
        super.remove(wrapper);
        // 创建大屏与功能的关系
        final List<DevScreenFuncRelation> devScreenFuncRelations = new ArrayList<>(funcNames.size());
        for (final String funcName : funcNames) {
            final DevScreenFuncRelation devScreenFuncRelation = new DevScreenFuncRelation();
            if (!Objects.isNull(tenantId)) devScreenFuncRelation.setTenantId(tenantId);
            devScreenFuncRelation.setScreenId(id);
            devScreenFuncRelation.setFuncName(funcName);
            devScreenFuncRelations.add(devScreenFuncRelation);
        }
        return super.saveBatch(devScreenFuncRelations);
    }

    /**
     * 根据主键查询功能。
     *
     * @param id 大屏主键。
     * @return 功能名称集合。
     */
    @Override
    public List<String> findScreenFunc(final long id) {
        final QueryWrapper<DevScreenFuncRelation> wrapper = createQueryWrapper();
        wrapper.lambda().eq(DevScreenFuncRelation::getScreenId, id);
        final List<DevScreenFuncRelation> devScreenFuncRelations = super.list(wrapper);
        return devScreenFuncRelations.stream()
                .map(DevScreenFuncRelation::getFuncName)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * @return 创建UpdateWrapper对象。
     */
    private UpdateWrapper<DevScreenFuncRelation> createUpdateWrapper() {
        return new UpdateWrapper<>();
    }

    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<DevScreenFuncRelation> createQueryWrapper() {
        return new QueryWrapper<>();
    }

}