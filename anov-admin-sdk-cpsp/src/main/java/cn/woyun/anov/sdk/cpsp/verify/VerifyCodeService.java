package cn.woyun.anov.sdk.cpsp.verify;

/**
 * 验证码业务处理接口。
 *
 * @author xuepeng
 */
public interface VerifyCodeService {

    /**
     * @return 创建验证码。
     */
    VerifyCode create();

    /**
     * 验证验证码是否正确。
     *
     * @param uuid 唯一标识。
     * @param code 验证码。
     * @return 是否正确。
     */
    boolean validate(final String uuid, final String code);

}
