package cn.woyun.anov.gateway.dev.serivce;

import cn.woyun.anov.gateway.dev.bean.resposne.DevAppLoginResponseBean;
import cn.woyun.anov.sdk.dev.entity.DevScreenClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 控制客户端的业务处理接口。
 *
 * @author xuepeng
 */
public interface DevAppServiceProxy {

    /**
     * 控制客户端登录。
     *
     * @param username 用户名。
     * @param password 密码。
     * @return 是否登录成功。
     */
    DevAppLoginResponseBean login(final String username, final String password);

    /**
     * 根据客户端唯一标识查询大屏唯一标识。
     *
     * @param code 客户端唯一标识。
     * @return 大屏唯一标识。
     */
    String findDevScreenKeyByClientCode(final String code);

    /**
     * 根据客户端唯一标识获取前三个在线大历史大屏信息。
     *
     * @param code 客户端唯一标识。
     * @return 客户端集合。
     */
    List<DevScreenClient> findOnlineTop3HistoryScreenByCode(final String code);

    /**
     * 认证控制器。
     *
     * @param key     大屏唯一标识。
     * @param request 客户端请求。
     * @return 是否认证通过。
     */
    boolean verify(final String key, final HttpServletRequest request);

}
