package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreen;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;
import cn.woyun.anov.sdk.dev.mapper.DevScreenClientMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 大屏客户端的业务处理实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-26
 */
@Service
@Slf4j
public class DevScreenClientServiceImpl extends ServiceImpl<DevScreenClientMapper, DevScreenClient> implements DevScreenClientService {

    /**
     * 创建大屏客户端。
     *
     * @param devScreenClient 大屏客户端对象。
     * @return 是否创建成功。
     */
    @Override
    @Transactional
    public boolean createOrUpdate(final DevScreenClient devScreenClient) {
        // 判断大屏地址是否可以连接
        DevScreen devScreen = new DevScreen();
        if (testRequestUrl(devScreenClient.getClientRequestUrl())) {
            devScreen.setScreenUrl(devScreenClient.getClientRequestUrl());
            devScreen.setScreenPreviewed(Boolean.TRUE);
        } else {
            devScreen.setScreenUrl(StringUtils.EMPTY);
            devScreen.setScreenPreviewed(Boolean.FALSE);
        }
        devScreen.setScreenKey(devScreenClient.getScreenKey());
        // 更新大屏的预览状态，并把大屏的检测次数+1
        devScreenService.updateScreenPriview(devScreen);
        // 查询客户端是否存在
        final DevScreenClient client = super.getOne(
                createQueryWrapper().lambda()
                        .eq(DevScreenClient::getClientCode, devScreenClient.getClientCode())
                        .eq(DevScreenClient::getScreenKey, devScreenClient.getScreenKey())
        );
        // 如果不存在则新建
        if (Objects.isNull(client)) {
            return super.save(devScreenClient);
        }
        // 如果存在则修改
        devScreenClient.setId(client.getId());
        return super.updateById(devScreenClient);
    }

    /**
     * 大屏客户端上线。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 是否上线成功。
     */
    @Override
    public boolean screenOnline(final String key, final String code) {
        final DevScreenClient client = new DevScreenClient();
        client.setClientOnlined(Boolean.TRUE);
        client.setClientOnlineTime(LocalDateTime.now());
        return super.update(client, createUpdateWrapper().lambda()
                .eq(DevScreenClient::getScreenKey, key)
                .eq(DevScreenClient::getClientCode, code));
    }

    /**
     * 移动控制端上线
     *
     * @param key    大屏唯一标识。
     * @param code   客户端唯一标识。
     * @param target 目标客户端唯一标识。
     * @return 是否上线成功。
     */
    @Override
    @Transactional
    public boolean controllerOnline(final String key, final String code, final String target) {
        // 控制器上线
        final DevScreenClient controllerClient = new DevScreenClient();
        controllerClient.setClientOnlined(Boolean.TRUE);
        controllerClient.setClientOnlineTime(LocalDateTime.now());
        controllerClient.setClientRemoteCode(target);
        super.update(controllerClient, createUpdateWrapper().lambda()
                .eq(DevScreenClient::getScreenKey, key)
                .eq(DevScreenClient::getClientCode, code));
        // 修改控制的大屏的hasChildren与isLeaf
        final DevScreenClient screenClient = new DevScreenClient();
        screenClient.setScreenHasChildren(Boolean.TRUE);
        screenClient.setScreenLeafed(Boolean.TRUE);
        return super.update(screenClient, createUpdateWrapper().lambda()
                .eq(DevScreenClient::getScreenKey, key)
                .eq(DevScreenClient::getClientCode, target));
    }

    /**
     * 大屏客户端下线。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 是否下线成功。
     */
    @Override
    @Transactional
    public boolean screenOffline(final String key, final String code) {
        final DevScreenClient client = new DevScreenClient();
        client.setClientOnlined(Boolean.FALSE);
        client.setClientOfflineTime(LocalDateTime.now());
        client.setScreenHasChildren(Boolean.FALSE);
        client.setScreenLeafed(Boolean.FALSE);
        return super.update(client, createUpdateWrapper().lambda()
                .eq(DevScreenClient::getScreenKey, key)
                .eq(DevScreenClient::getClientCode, code));
    }

    /**
     * 移动控制端下线。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 是否下线成功。
     */
    @Override
    public boolean controllerOffline(final String key, final String code) {
        final DevScreenClient client = new DevScreenClient();
        client.setClientOnlined(Boolean.FALSE);
        client.setClientOfflineTime(LocalDateTime.now());
        return super.update(client, createUpdateWrapper().lambda()
                .eq(DevScreenClient::getScreenKey, key)
                .eq(DevScreenClient::getClientCode, code));
    }

    /**
     * 根据客户端唯一标识查询客户端信息。
     *
     * @param code 客户端唯一标识。
     * @return 客户端信息。
     */
    public DevScreenClient findScreenByCode(final String code) {
        return super.getOne(
                createQueryWrapper().lambda().eq(DevScreenClient::getClientCode, code),
                false
        );
    }

    /**
     * 根据客户端唯一标识获取前三个在线大历史大屏信息。
     *
     * @param code 客户端唯一标识。
     * @return 客户端集合。
     */
    public List<DevScreenClient> findOnlineTop3HistoryScreenByCode(final String code) {
        // 查询出当前控制器连接过的大屏信息
        final List<DevScreenClient> clients = super.list(
                createQueryWrapper().lambda()
                        .eq(DevScreenClient::getClientCode, code)
                        .orderByDesc(DevScreenClient::getClientOfflineTime)
        );
        // 查询最近连接过的前三个在线的大屏
        final List<String> codes = clients.stream().map(DevScreenClient::getClientRemoteCode).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(codes)) {
            return new ArrayList<>();
        }
        return super.list(
                createQueryWrapper().lambda()
                        .in(DevScreenClient::getClientCode, codes)
                        .eq(DevScreenClient::getClientOnlined, Boolean.TRUE).last("limit 3")
        );
    }

    /**
     * 根据项目唯一标识和客户端唯一标识查询客户端信息。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 客户端信息。
     */
    @Override
    public DevScreenClient findScreenByKeyAndCode(final String key, final String code) {
        return super.getOne(
                createQueryWrapper().lambda()
                        .eq(DevScreenClient::getScreenKey, key)
                        .eq(DevScreenClient::getClientCode, code),
                false
        );
    }

    /**
     * 根据客户端唯一标识查询要发送的客户端信息。
     *
     * @param key  大屏唯一标识。
     * @param code 客户端唯一标识。
     * @return 客户端信息集合。
     */
    @Override
    public List<DevScreenClient> findSendTargetByKeyAndCode(final String key, final String code) {
        return super.list(createQueryWrapper().lambda()
                .eq(DevScreenClient::getScreenKey, key)
                .and(o -> o.eq(DevScreenClient::getClientCode, code)
                        .or()
                        .eq(DevScreenClient::getClientRemoteCode, code)
                )
        );
    }

    /**
     * 检测客户端唯一标识是否存在。
     *
     * @param clientCode 客户端唯一标识。
     * @return 是否存在。
     */
    @Override
    public boolean checkClientCodeExist(final String clientCode) {
        return super.count(
                createQueryWrapper().lambda().eq(DevScreenClient::getClientCode, clientCode)
        ) > 0;
    }

    /**
     * 根据条件分页查询大屏客户端。
     *
     * @param page            分页。
     * @param devScreenClient 查询条件。
     * @return 大屏客户端分页信息。
     */
    @Override
    public Page<DevScreenClient> findByPageAndCondition(final Page<DevScreenClient> page, final DevScreenClient devScreenClient) {
        return super.page(page, createQueryWrapper(devScreenClient));
    }

    /**
     * 根据大屏标识和客户端标识查询所有远程控制的设备。
     *
     * @param key  大屏标识。
     * @param code 客户端标识。
     * @return 远程控制的设备集合。
     */
    @Override
    public List<DevScreenClient> findRemoteClientByKeyAndCode(final String key, final String code) {
        return super.list(
                createQueryWrapper().lambda()
                        .eq(DevScreenClient::getScreenKey, key)
                        .eq(DevScreenClient::getClientRemoteCode, code)
                        .eq(DevScreenClient::getClientOnlined, Boolean.TRUE)
                        .orderByDesc(DevScreenClient::getClientOnlineTime)
        );
    }

    /**
     * 测试大屏Url是否可以方案。
     *
     * @param requestUrl 大屏Url。
     * @return 是否可以访问。
     */
    private boolean testRequestUrl(final String requestUrl) {
        try {
            final URL url = new URL(requestUrl);
            final URLConnection conn = url.openConnection();
            conn.setConnectTimeout(3000);
            conn.connect();
            return true;
        } catch (IOException e) {
            log.error("test request url failed, url is {}, cause is {}",
                    requestUrl, e.getMessage());
        }
        return false;
    }

    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<DevScreenClient> createQueryWrapper() {
        return new QueryWrapper<>();
    }


    /**
     * @return 创建QueryWrapper对象。
     */
    private QueryWrapper<DevScreenClient> createQueryWrapper(final DevScreenClient devScreenClient) {
        final QueryWrapper<DevScreenClient> wrapper = createQueryWrapper();
        final LambdaQueryWrapper<DevScreenClient> criteria = wrapper.lambda();
        if (StringUtils.isNotBlank(devScreenClient.getClientType())) {
            criteria.eq(DevScreenClient::getClientType, devScreenClient.getClientType());
        }
        if (StringUtils.isNotBlank(devScreenClient.getScreenKey())) {
            criteria.eq(DevScreenClient::getScreenKey, devScreenClient.getScreenKey());
        }
        if (StringUtils.isNotBlank(devScreenClient.getClientCode())) {
            criteria.in(DevScreenClient::getClientCode, devScreenClient.getClientCode());
        }
        if (ObjectUtils.allNotNull(devScreenClient.getBeginTime(), devScreenClient.getEndTime())) {
            criteria.between(DevScreenClient::getClientOnlineTime, devScreenClient.getBeginTime(), devScreenClient.getEndTime());
        }
        if (StringUtils.isNotBlank(devScreenClient.getClientRemoteCode())) {
            criteria.in(DevScreenClient::getClientRemoteCode, devScreenClient.getClientRemoteCode());
        }
        criteria.orderByDesc(DevScreenClient::getClientOnlined).orderByDesc(DevScreenClient::getClientOnlineTime);
        return wrapper;
    }

    /**
     * @return 创建UpdateWrapper对象。
     */
    private UpdateWrapper<DevScreenClient> createUpdateWrapper() {
        return new UpdateWrapper<>();
    }

    /**
     * 自动装配大屏的业务处理接口。
     *
     * @param devScreenService 大屏的业务处理接口。
     */
    @Autowired
    public void setDevScreenService(DevScreenService devScreenService) {
        this.devScreenService = devScreenService;
    }

    /**
     * 大屏的业务处理接口。
     */
    private DevScreenService devScreenService;

}