package cn.woyun.anov.gateway.dev.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 大屏API的自定义返回码。
 *
 * @author xuepeng
 */
public enum DevScreenResultStatus implements HttpResultStatus {

    /**
     * Token解密失败。
     */
    DEV_SCREEN_TOKEN_DECODE(50001, "Token解密失败。"),

    /**
     * Token格式不正确。
     */
    DEV_SCREEN_TOKEN_FORMAT(50002, "Token格式不正确。"),

    /**
     * Token不正确。
     */
    DEV_SCREEN_TOKEN_INCORREC(50003, "Token不正确。"),

    /**
     * 请求时间与服务器时间不匹配。
     */
    DEV_SCREEN_REQUEST_TIME_RANGE(50004, "请求时间与服务器时间不匹配。"),

    /**
     * 大屏不存在。
     */
    DEV_SCREEN_NOT_FOUND(50005, "大屏不存在。"),

    /**
     * 大屏有效期已过期。
     */
    DEV_SCREEN_EXPIRE(50006, "大屏有效期已过期。"),

    /**
     * 大屏状态为不可用。
     */
    DEV_SCREEN_STATUS(50007, "大屏状态为不可用。"),

    /**
     * 要控制的大屏客户度不存在。
     */
    DEV_SCREEN_PIC_UPLOAD(50008, "上传大屏图片失败。"),


    // 手机客户端使用的错误码-------------------------------

    /**
     * 密码解密错误。
     */
    DEV_APP_PASSWORD_DECODE(51001, "登录密码无法解密。"),

    /**
     * 验证码不正确。
     */
    DEV_APP_VERIFY_CODE_INCORREC(51002, "验证码不正确。"),

    /**
     * 验证码不正确。
     */
    DEV_APP_LOGIN_FAILED(51003, "用户名或密码不正确。"),

    /**
     * 验证码不正确。
     */
    DEV_APP_TARGET_NOT_FOUND(51004, "要控制的大屏不存在。"),

    /**
     * 验证码不正确。
     */
    DEV_APP_TARGET_OFFLINE(51005, "要控制的大屏未上线。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    DevScreenResultStatus(
            final int code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @return 获得状态编码。
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * @return 获得状态描述。
     */
    @Override
    public String getDesc() {
        return desc;
    }

    /**
     * 状态编码。
     */
    private final int code;
    /**
     * 状态描述。
     */
    private final String desc;

}
