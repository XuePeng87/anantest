package cn.woyun.anov.sdk.mgt.service.user;

/**
 * 用户密码处理器。
 *
 * @author xuepeng
 */
public interface SysUserPasswordHandler {

    /**
     * @return 生成密码。
     */
    String generate();

    /**
     * 加密密码。
     *
     * @param password 对密码进行加密。
     * @return 加密后的密码。
     */
    String encode(final String password);

    /**
     * 验证用户旧密码是否正确。
     *
     * @param oldPassword  旧密码。
     * @param currPassword 当前密码。
     * @return 旧密码是否正确。
     */
    boolean matches(final String oldPassword, final String currPassword);

}
