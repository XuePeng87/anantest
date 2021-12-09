package cn.woyun.anov.sdk.mgt.service.role;

import cn.woyun.anov.sdk.mgt.entity.SysRoleFuncRelation;
import cn.woyun.anov.sdk.mgt.mapper.SysRoleFuncRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色与功能关系的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysRoleFuncRelationServiceImpl extends ServiceImpl<SysRoleFuncRelationMapper, SysRoleFuncRelation> implements SysRoleFuncRelationService {

    /**
     * 保存角色下的功能。
     *
     * @param id      部门主键。
     * @param funcIds 功能主键集合。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveRoleToFunc(final long id, final Collection<Long> funcIds) {
        // 删除角色与功能的关系
        super.remove(createUpdateWrapper().lambda().eq(SysRoleFuncRelation::getRoleId, id));
        // 创建角色与功能的关系
        final List<SysRoleFuncRelation> sysRoleFuncRelations = new ArrayList<>(funcIds.size());
        for (final long funcId : funcIds) {
            final SysRoleFuncRelation sysRoleFuncRelation = new SysRoleFuncRelation();
            sysRoleFuncRelation.setRoleId(id);
            sysRoleFuncRelation.setFuncId(funcId);
            sysRoleFuncRelations.add(sysRoleFuncRelation);
        }
        return super.saveBatch(sysRoleFuncRelations);
    }

    /**
     * 根据功能主键删除角色下的功能。
     *
     * @param funcId 功能主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteRoleFuncByFuncId(final long funcId) {
        return super.remove(
                createUpdateWrapper().lambda().eq(SysRoleFuncRelation::getFuncId, funcId)
        );
    }

    /**
     * 角色下是否存在功能。
     *
     * @param id 功能主键。
     * @return 是否存在功能。
     */
    @Override
    public boolean hasFunc(final long id) {
        return super.count(
                createQueryWrapper().lambda().eq(SysRoleFuncRelation::getRoleId, id)
        ) > 0;
    }

    /**
     * 根据主键查询角色下的功能。
     *
     * @param id 角色主键。
     * @return 角色下的功能主键集合。
     */
    @Override
    public List<Long> findFuncIdsById(final long id) {
        return this.findFuncIdsById(Collections.singletonList(id));
    }

    /**
     * 根据主键查询角色下的功能。
     *
     * @param id 角色主键集合。
     * @return 角色下的功能主键集合。
     */
    @Override
    public List<Long> findFuncIdsById(final List<Long> id) {
        final List<SysRoleFuncRelation> sysRoleFuncRelations = super.list(
                createQueryWrapper().lambda().in(SysRoleFuncRelation::getRoleId, id)
        );
        // 获取功能主键并去重
        return sysRoleFuncRelations.stream()
                .map(SysRoleFuncRelation::getFuncId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<SysRoleFuncRelation> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * @return 创建UpdateWrapper对象。
     */
    private UpdateWrapper<SysRoleFuncRelation> createUpdateWrapper() {
        return new UpdateWrapper<>();
    }

}