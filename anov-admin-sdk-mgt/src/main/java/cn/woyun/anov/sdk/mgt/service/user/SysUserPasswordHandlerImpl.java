package cn.woyun.anov.sdk.mgt.service.user;

import cn.woyun.anov.config.mgt.SysUserConfiguration;
import cn.woyun.anov.random.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        return passwordEncoder.encode(password);
    }

    /**
     * 验证用户旧密码是否正确。
     *
     * @param oldPassword  旧密码。
     * @param currPassword 当前密码。
     * @return 旧密码是否正确。
     */
    @Override
    public boolean matches(final String oldPassword, final String currPassword) {
        return passwordEncoder.matches(oldPassword, currPassword);
    }

    /**
     * 自动装配密码加密工具。
     *
     * @param passwordEncoder 密码加密工具。
     */
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
     * 密码加密工具。
     */
    private PasswordEncoder passwordEncoder;

    /**
     * 用户管理配置信息。
     */
    private SysUserConfiguration sysUserConfiguration;

}
