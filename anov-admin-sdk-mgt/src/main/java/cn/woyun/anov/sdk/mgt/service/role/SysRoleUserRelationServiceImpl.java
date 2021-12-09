package cn.woyun.anov.sdk.mgt.service.role;

import cn.woyun.anov.sdk.mgt.entity.SysRoleUserRelation;
import cn.woyun.anov.sdk.mgt.mapper.SysRoleUserRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色与用户关系的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysRoleUserRelationServiceImpl extends ServiceImpl<SysRoleUserRelationMapper, SysRoleUserRelation> implements SysRoleUserRelationService {

    /**
     * 保存角色下的用户。
     *
     * @param id      部门主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveRoleToUser(final long id, final Collection<Long> userIds) {
        // 删除角色与用户的关系
        super.remove(createUpdateWrapper().lambda().eq(SysRoleUserRelation::getRoleId, id));
        // 保存角色与用户的关系
        final List<SysRoleUserRelation> sysRoleUserRelations = new ArrayList<>(userIds.size());
        for (final long userId : userIds) {
            final SysRoleUserRelation sysRoleUserRelation = new SysRoleUserRelation();
            sysRoleUserRelation.setRoleId(id);
            sysRoleUserRelation.setUserId(userId);
            sysRoleUserRelations.add(sysRoleUserRelation);
        }
        return super.saveBatch(sysRoleUserRelations);
    }

    /**
     * 保存用户下的角色。
     *
     * @param ids    角色主键集合。
     * @param userId 用户主键。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveUserToRole(final Collection<Long> ids, final long userId) {
        // 删除角色与用户的关系
        super.remove(createUpdateWrapper().lambda().eq(SysRoleUserRelation::getUserId, userId));
        // 保存角色与用户的关系
        final List<SysRoleUserRelation> sysRoleUserRelations = new ArrayList<>(ids.size());
        for (final long id : ids) {
            final SysRoleUserRelation sysRoleUserRelation = new SysRoleUserRelation();
            sysRoleUserRelation.setRoleId(id);
            sysRoleUserRelation.setUserId(userId);
            sysRoleUserRelations.add(sysRoleUserRelation);
        }
        return super.saveBatch(sysRoleUserRelations);
    }

    /**
     * 根据用户主键删除角色下的用户。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteRoleUserByUserId(final long userId) {
        final UpdateWrapper<SysRoleUserRelation> wrapper = createUpdateWrapper();
        wrapper.lambda().eq(SysRoleUserRelation::getUserId, userId);
        return super.remove(wrapper);
    }

    /**
     * 角色下是否存在用户。
     *
     * @param id 角色主键。
     * @return 是否存在用户。
     */
    @Override
    public boolean hasUser(final long id) {
        return super.count(
                createQueryWrapper().lambda().eq(SysRoleUserRelation::getRoleId, id)
        ) > 0;
    }

    /**
     * 根据主键查询角色下的用户。
     *
     * @param id 角色主键。
     * @return 角色下的用户主键集合。
     */
    @Override
    public List<Long> findUserIdsById(final long id) {
        final List<SysRoleUserRelation> sysRoleUserRelations = super.list(
                createQueryWrapper().lambda().eq(SysRoleUserRelation::getRoleId, id)
        );
        // 获取用户主键并去重
        return sysRoleUserRelations.stream()
                .map(SysRoleUserRelation::getUserId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 根据用户主键查询该用户拥有的角色主键。
     *
     * @param userId 用户主键。
     * @return 角色主键集合。
     */
    @Override
    public List<Long> findRoleIdsByUserId(final long userId) {
        // 查询用户拥有的角色
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleUserRelation::getUserId, userId);
        final List<SysRoleUserRelation> sysRoleUserRelations = super.list(wrapper);
        // 获取角色主键并去重
        return sysRoleUserRelations.stream()
                .map(SysRoleUserRelation::getRoleId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * @return 创建QueryWrapper。
     */
    private QueryWrapper<SysRoleUserRelation> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * @return 创建UpdateWrapper。
     */
    private UpdateWrapper<SysRoleUserRelation> createUpdateWrapper() {
        return new UpdateWrapper<>();
    }

}