package cn.woyun.anov.sdk.dev.service;

import cn.woyun.anov.sdk.dev.entity.DevScreenPic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 大屏图片的业务处理接口。
 * </p>
 *
 * @author xuepeng
 * @since 2021-11-17
 */
public interface DevScreenPicService extends IService<DevScreenPic> {

    /**
     * 保存大屏图片。
     *
     * @param devScreenPic 大屏图片对象。
     * @return 是否保存成功。
     */
    boolean saveDevScreenPic(final DevScreenPic devScreenPic);

    /**
     * 根据标识查询大屏的截图。
     *
     * @param screenKey 大屏标识。
     * @return 大屏截图集合。
     */
    List<String> findDevScreenPicByKey(final String screenKey);

}