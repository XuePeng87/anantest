package cn.woyun.anov.sdk.dev.service.core;

import cn.woyun.anov.sdk.dev.entity.DevScreen;

/**
 * 大屏芯片生成器。
 *
 * @author xuepeng
 */
public interface ScreenCoreGenerator {

    /**
     * 生成大屏芯片。
     *
     * @param devScreen 大屏信息。
     * @return 芯片。
     */
    String generate(final DevScreen devScreen);

}
