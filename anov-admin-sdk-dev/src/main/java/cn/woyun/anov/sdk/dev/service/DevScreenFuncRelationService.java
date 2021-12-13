package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreenFuncRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 大屏与功能管理的业务处理接口。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-17
 */
public interface DevScreenFuncRelationService extends IService<DevScreenFuncRelation> {

    /**
     * 保存大屏与功能的关系。
     *
     * @param id        大屏主键。
     * @param tenantId  大屏租户主键。
     * @param funcNames 功能名称。
     * @return 是否保存成功。
     */
    boolean saveScreenFunc(final long id, final Long tenantId, final Collection<String> funcNames);

    /**
     * 根据主键查询功能。
     *
     * @param id 大屏主键。
     * @return 功能名称集合。
     */
    List<String> findScreenFunc(final long id);

}