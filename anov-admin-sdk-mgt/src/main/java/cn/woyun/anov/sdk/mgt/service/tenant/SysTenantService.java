package cn.woyun.anov.sdk.mgt.service.tenant;

import cn.woyun.anov.sdk.mgt.entity.SysTenant;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * <p>
 * 租户的业务处理接口。
 * </p>
 *
 * @author xuepeng
 */
public interface SysTenantService extends IService<SysTenant> {

    /**
     * 创建租户。
     *
     * @param sysTenant 租户对象。
     * @return 是否创建成功。
     */
    boolean create(final SysTenant sysTenant);

    /**
     * 修改租户。
     *
     * @param sysTenant 租户对象。
     * @return 是否修改成功。
     */
    boolean update(final SysTenant sysTenant);

    /**
     * 根据主键批量删除租户。
     *
     * @param ids 组件集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final Collection<Long> ids);

    /**
     * 检测租户电话是否存在。
     *
     * @param id    租户主键。
     * @param phone 电话。
     * @return 电话是否存在。
     */
    boolean checkPhoneExisted(final long id, final String phone);

    /**
     * 检测租户邮箱是否存在。
     *
     * @param id    租户主键。
     * @param email 邮箱。
     * @return 邮箱是否存在。
     */
    boolean checkEmailExisted(final long id, final String email);

    /**
     * 根据主键查询租户。
     *
     * @param id 租户主键。
     * @return 租户。
     */
    SysTenant findById(final long id);

    /**
     * 根据条件分页查询。
     *
     * @param page      分页信息。
     * @param sysTenant 查询条件。
     * @return 租户集合。
     */
    Page<SysTenant> findByPageAndCondition(final Page<SysTenant> page, final SysTenant sysTenant);

}