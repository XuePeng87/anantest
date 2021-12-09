package cn.woyun.anov.sdk.mgt.service.func;

import cn.woyun.anov.biz.ExistsUtil;
import cn.woyun.anov.biz.TreeUtil;
import cn.woyun.anov.sdk.mgt.entity.SysFunc;
import cn.woyun.anov.sdk.mgt.exception.SysFuncCannotDeleteException;
import cn.woyun.anov.sdk.mgt.exception.SysFuncComponentNameDuplicateException;
import cn.woyun.anov.sdk.mgt.exception.SysFuncNameDuplicateException;
import cn.woyun.anov.sdk.mgt.mapper.SysFuncMapper;
import cn.woyun.anov.sdk.mgt.service.role.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 功能的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 */
@Service
public class SysFuncServiceImpl extends ServiceImpl<SysFuncMapper, SysFunc> implements SysFuncService {

    /**
     * 创建功能。
     *
     * @param sysFunc SysFunc实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysFunc sysFunc) {
        if (checkNameExisted(0L, sysFunc.getFuncName())) {
            throw new SysFuncNameDuplicateException("功能名称已存在。");
        }
        if (checkComponentNameExisted(0L, sysFunc.getFuncComponentName())) {
            throw new SysFuncComponentNameDuplicateException("功能组件已存在。");
        }
        sysFunc.setCreateTime(LocalDateTime.now());
        sysFunc.setModifyTime(LocalDateTime.now());
        return super.save(sysFunc);
    }

    /**
     * 修改功能。
     *
     * @param sysFunc 功能实体类。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysFunc sysFunc) {
        if (checkNameExisted(sysFunc.getId(), sysFunc.getFuncName())) {
            throw new SysFuncNameDuplicateException("功能名称已存在。");
        }
        if (checkComponentNameExisted(sysFunc.getId(), sysFunc.getFuncComponentName())) {
            throw new SysFuncComponentNameDuplicateException("功能组件已存在。");
        }
        sysFunc.setModifyTime(LocalDateTime.now());
        return super.updateById(sysFunc);
    }

    /**
     * 根据主键批量删除功能。
     * 如果功能下有子功能，则不可删除。
     * 删除功能与角色的关系。
     *
     * @param ids 功能主键集合。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteByIds(final Collection<Long> ids) {
        for (final long id : ids) {
            // 如果功能下有子功能，则不能删除
            if (hasChildren(id)) {
                throw new SysFuncCannotDeleteException("功能[" + id + "]下有子功能，无法删除。");
            }
            sysRoleService.deleteRoleFunc(id);
        }
        return super.removeByIds(ids);
    }

    /**
     * 检测功能名称是否存在。
     *
     * @param id   功能主键。
     * @param name 功能名称。
     * @return 是否存在。
     */
    @Override
    public boolean checkNameExisted(final long id, final String name) {
        final List<SysFunc> sysFuncs = super.list(
                createQueryWrapper().lambda().eq(SysFunc::getFuncName, name)
        );
        return ExistsUtil.exists(sysFuncs, id == 0 ? StringUtils.EMPTY : String.valueOf(id), "Id");
    }

    /**
     * 检测组件名称是否存在。
     *
     * @param id            功能主键。
     * @param componentName 组件名称。
     * @return 是否存在。
     */
    @Override
    public boolean checkComponentNameExisted(final long id, final String componentName) {
        final List<SysFunc> sysFuncs = super.list(
                createQueryWrapper().lambda().eq(SysFunc::getFuncComponentName, componentName)
        );
        return ExistsUtil.exists(sysFuncs, id == 0 ? StringUtils.EMPTY : String.valueOf(id), "Id");
    }

    /**
     * 根据条件查询功能。
     *
     * @param sysFunc 查询条件。
     * @return 功能集合。
     */
    @Override
    public List<SysFunc> findByCondition(final SysFunc sysFunc) {
        final QueryWrapper<SysFunc> queryWrapper = createQueryWrapper(sysFunc);
        return super.list(queryWrapper);
    }

    /**
     * 根据功能主键查询功能。
     *
     * @param ids 功能主键集合。
     * @return 功能集合。
     */
    @Override
    public List<SysFunc> findByIds(final List<Long> ids) {
        final QueryWrapper<SysFunc> queryWrapper = createQueryWrapper();
        queryWrapper.lambda()
                .in(SysFunc::getId, ids)
                .eq(SysFunc::getFuncVisible, Boolean.TRUE)
                .orderByAsc(SysFunc::getFuncSort);
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        final List<SysFunc> funcs = super.list(queryWrapper);
        return TreeUtil.format(funcs, "0");
    }

    /**
     * @return 查询全部功能。
     */
    @Override
    public List<SysFunc> findAll() {
        final List<SysFunc> funcs = super.list(
                createQueryWrapper().lambda().orderByAsc(SysFunc::getFuncSort)
        );
        return TreeUtil.format(funcs, "0");
    }

    /**
     * @return 查询全部可见的功能。
     */
    @Override
    public List<SysFunc> findVisible() {
        final List<SysFunc> funcs = super.list(
                createQueryWrapper().lambda()
                        .eq(SysFunc::getFuncVisible, Boolean.TRUE)
                        .orderByAsc(SysFunc::getFuncSort)
        );
        return TreeUtil.format(funcs, "0");
    }

    /**
     * 查询功能下是否有子功能。
     *
     * @param id 功能主键。
     * @return 是否有子功能。
     */
    private boolean hasChildren(final long id) {
        final int result = super.count(
                createQueryWrapper().lambda().eq(SysFunc::getPid, id)
        );
        return result > 0;
    }

    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<SysFunc> createQueryWrapper() {
        return new QueryWrapper<>();
    }

    /**
     * 根据查询条件创建QueryWrapper。
     *
     * @param sysFunc 查询条件。
     * @return QueryWrapper对象。
     */
    private QueryWrapper<SysFunc> createQueryWrapper(final SysFunc sysFunc) {
        final QueryWrapper<SysFunc> queryWrapper = createQueryWrapper();
        final LambdaQueryWrapper<SysFunc> criteria = queryWrapper.lambda();
        if (!Objects.isNull(sysFunc.getPid())) {
            criteria.eq(SysFunc::getPid, sysFunc.getPid());
        }
        if (StringUtils.isNotBlank(sysFunc.getFuncName())) {
            criteria.like(SysFunc::getFuncName, sysFunc.getFuncName());
        }
        criteria.orderByAsc(SysFunc::getFuncSort);
        return queryWrapper;
    }

    /**
     * 自动装配角色的业务处理接口。
     *
     * @param sysRoleService 角色的业务处理接口。
     */
    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 角色的业务处理接口。
     */
    private SysRoleService sysRoleService;

}