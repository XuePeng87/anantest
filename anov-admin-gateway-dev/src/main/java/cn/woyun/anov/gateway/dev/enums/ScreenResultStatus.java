package cn.woyun.anov.gateway.dev.enums;

import cn.woyun.anov.http.HttpResultStatus;

/**
 * 大屏API的自定义返回码。
 *
 * @author xuepeng
 */
public enum ScreenResultStatus implements HttpResultStatus {

    /**
     * Token解密失败。
     */
    TOKEN_DECODE(50001, "Token解密失败。"),

    /**
     * Token格式不正确。
     */
    TOKEN_FORMAT(50002, "Token格式不正确。"),

    /**
     * Token不正确。
     */
    TOKEN_INCORREC(50003, "Token不正确。"),

    /**
     * 请求时间与服务器时间不匹配。
     */
    REQUEST_TIME_RANGE(50004, "请求时间与服务器时间不匹配。"),

    /**
     * 大屏不存在。
     */
    SCREEN_NOT_FOUND(50005, "大屏不存在。"),

    /**
     * 大屏有效期已过期。
     */
    SCREEN_EXPIRE(50006, "大屏有效期已过期。"),

    /**
     * 大屏状态为不可用。
     */
    SCREEN_STATUS(50007, "大屏状态为不可用。"),

    /**
     * 要控制的大屏客户度不存在。
     */
    SCREEN_PIC_UPLOAD(50008, "上传大屏图片失败。");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    ScreenResultStatus(
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
