package cn.woyun.anov.sdk.mgt.service.user;

import cn.woyun.anov.codec.MD5Util;
import cn.woyun.anov.config.mgt.SysUserConfiguration;
import cn.woyun.anov.random.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户密码处理器的实现类。
 *
 * @author xuepeng
 */
@Component("sysUserPasswordHandlerImpl")
@Slf4j
public class SysUserPasswordHandlerImpl implements SysUserPasswordHandler {

    /**
     * @return 生成密码。
     */
    @Override
    public String generate() {
        final String password = sysUserConfiguration.getPasswordProfix()
                + RandomUtil.getSixDigitsString();
        log.info("生成的默认密码为：" + password);
        return encode(password);
    }

    /**
     * 加密密码。
     *
     * @param password 对密码进行加密。
     * @return 加密后的密码。
     */
    @Override
    public String encode(final String password) {
        return MD5Util.encode(password);
    }

    /**
     * 自动装配用户管理配置信息。
     *
     * @param sysUserConfiguration 用户管理配置信息。
     */
    @Autowired
    public void setSysUserConfiguration(SysUserConfiguration sysUserConfiguration) {
        this.sysUserConfiguration = sysUserConfiguration;
    }

    /**
     * 用户管理配置信息。
     */
    private SysUserConfiguration sysUserConfiguration;

}
