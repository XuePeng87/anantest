package cn.woyun.anov.sdk.mgt.service.dept;

import cn.woyun.anov.biz.TreeUtil;
import cn.woyun.anov.sdk.mgt.entity.SysDept;
import cn.woyun.anov.sdk.mgt.enums.DeptStatus;
import cn.woyun.anov.sdk.mgt.exception.SysDeptCannotDeleteException;
import cn.woyun.anov.sdk.mgt.mapper.SysDeptMapper;
import cn.woyun.anov.sdk.mgt.service.user.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 创建部门。
     *
     * @param sysDept 部门实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysDept sysDept) {
        sysDept.setCreateTime(LocalDateTime.now());
        sysDept.setModifyTime(LocalDateTime.now());
        return super.save(sysDept);
    }

    /**
     * 修改部门。
     *
     * @param sysDept 部门对象。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysDept sysDept) {
        sysDept.setModifyTime(LocalDateTime.now());
        return super.updateById(sysDept);
    }

    /**
     * 根据主键批量删除部门。
     * 部门下有子部门，不可删除。
     * 部门下有用户，不可删除。
     *
     * @param ids 部门主键集合。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByIds(final Collection<Long> ids) {
        // 判断部门下是否存在用户
        for (final Long id : ids) {
            if (hasChildren(id)) {
                throw new SysDeptCannotDeleteException("部门[" + id + "]下存在子部门，无法删除。");
            }
            if (hasUser(id)) {
                throw new SysDeptCannotDeleteException("部门[" + id + "]下存在用户，无法删除。");
            }
        }
        // 删除部门
        return super.removeByIds(ids);
    }

    /**
     * 根据条件查询部门。
     *
     * @param sysDept 查询条件。
     * @return 部门集合。
     */
    @Override
    public List<SysDept> findByCondition(final SysDept sysDept) {
        final QueryWrapper<SysDept> queryWrapper = createQueryWrapper(sysDept);
        return super.list(queryWrapper);
    }

    /**
     * 根据主键查询部门。
     *
     * @param id 主键。
     * @return 部门。
     */
    @Override
    public SysDept findById(final long id) {
        return super.getById(id);
    }

    /**
     * @return 查询主键和名称的Map。
     */
    @Override
    public Map<Long, String> findAllToMap() {
        final List<SysDept> depts = super.list();
        return depts.stream().collect(Collectors.toMap(SysDept::getId, SysDept::getDeptName));
    }

    /**
     * @return 查询全部部门。
     */
    @Override
    public List<SysDept> findAll() {
        final List<SysDept> depts = super.list(
                createQueryWrapper().lambda()
                        .eq(SysDept::getDeptStatus, DeptStatus.ENABLE)
                        .orderByDesc(SysDept::getDeptSort)
        );
        return TreeUtil.format(depts, "0");
    }

    /**
     * 判断部门下是否有子部门。
     *
     * @param id 部门主键。
     * @return 是否有子部门。
     */
    private boolean hasChildren(final long id) {
        return super.count(
                createQueryWrapper().lambda().eq(SysDept::getPid, id)
        ) > 0;
    }

    /**
     * 判断部门下是否有用户。
     *
     * @param id 部门主键。
     * @return 是否有用户。
     */
    private boolean hasUser(final long id) {
        return sysUserService.findUserCountByDeptId(id) > 0;
    }

    /**
     * @return 创建QueryWrapper。
     */
    private QueryWrapper<SysDept> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 根据查询条件创建QueryWrapper。
     *
     * @param sysDept 查询条件。
     * @return QueryWrapper对象。
     */
    private QueryWrapper<SysDept> createQueryWrapper(final SysDept sysDept) {
        final QueryWrapper<SysDept> queryWrapper = createQueryWrapper();
        final LambdaQueryWrapper<SysDept> criteria = queryWrapper.lambda();
        if (!Objects.isNull(sysDept.getPid())) {
            criteria.eq(SysDept::getPid, sysDept.getPid());
        }
        if (StringUtils.isNotBlank(sysDept.getDeptName())) {
            criteria.like(SysDept::getDeptName, sysDept.getDeptName());
        }
        if (!Objects.isNull(sysDept.getDeptStatus())) {
            criteria.eq(SysDept::getDeptStatus, sysDept.getDeptStatus());
        }
        criteria.orderByAsc(SysDept::getDeptSort);
        return queryWrapper;
    }

    /**
     * 自动装配用户的业务处理接口。
     *
     * @param sysUserService 用户的业务处理接口。
     */
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 用户的业务处理接口。
     */
    private SysUserService sysUserService;

}