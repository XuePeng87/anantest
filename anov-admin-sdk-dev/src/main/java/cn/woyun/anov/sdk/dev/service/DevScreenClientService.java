package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 大屏客户端的业务处理接口。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-26
 */
public interface DevScreenClientService extends IService<DevScreenClient> {

    /**
     * 创建大屏客户端。
     *
     * @param devScreenClient 大屏客户端对象。
     * @return 是否创建成功。
     */
    boolean createOrUpdate(final DevScreenClient devScreenClient);

    /**
     * 根据客户端唯一标识查询客户端信息。
     *
     * @param code 客户端唯一标识。
     * @return 客户端信息。
     */
    DevScreenClient findScreenByCode(final String code);

    /**
     * 根据客户端唯一标识获取前三个在线大历史大屏信息。
     *
     * @param code 客户端唯一标识。
     * @return 客户端集合。
     */
    List<DevScreenClient> findOnlineTop3HistoryScreenByCode(final String code);

    /**
     * 根据项目唯一标识和客户端唯一标识查询客户端信息。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 客户端信息。
     */
    DevScreenClient findScreenByKeyAndCode(final String key, final String code);

    /**
     * 根据客户端唯一标识查询要发送的客户端信息。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 客户端信息集合。
     */
    List<DevScreenClient> findSendTargetByKeyAndCode(final String key, final String code);

    /**
     * 检测客户端唯一标识是否存在。
     *
     * @param clientCode 客户端唯一标识。
     * @return 是否存在。
     */
    boolean checkClientCodeExist(final String clientCode);

    /**
     * 大屏客户端上线。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 是否上线成功。
     */
    boolean screenOnline(final String key, final String code);

    /**
     * 移动控制端上线。
     *
     * @param key    大屏唯一标识。
     * @param code   客户端唯一标识。
     * @param target 目标客户端唯一标识。
     * @return 是否上线成功。
     */
    boolean controllerOnline(final String key, final String code, final String target);

    /**
     * 大屏客户端下线。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 是否下线成功。
     */
    boolean screenOffline(final String key, final String code);

    /**
     * 移动控制端下线。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 是否下线成功。
     */
    boolean controllerOffline(final String key, final String code);

    /**
     * 根据条件分页查询大屏客户端。
     *
     * @param page            分页。
     * @param devScreenClient 查询条件。
     * @return 大屏客户端分页信息。
     */
    Page<DevScreenClient> findByPageAndCondition(final Page<DevScreenClient> page, final DevScreenClient devScreenClient);

    /**
     * 根据大屏标识和客户端标识查询所有远程控制的设备。
     *
     * @param key  大屏标识。
     * @param code 客户端标识。
     * @return 远程控制的设备集合。
     */
    List<DevScreenClient> findRemoteClientByKeyAndCode(final String key, final String code);

}