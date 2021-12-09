package cn.woyun.anov.gateway.management.consts;

/**
 * BMS的HttpHeader头常量。
 *
 * @author xuepeng
 */
public class WebConstants {

    /**
     * 构造函数。
     */
    private WebConstants() {
    }

    /**
     * 访问令牌
     */
    public static final String ACCESS_TOKEN = "X-Access-Token";

    /**
     * 登录用户Session Key。
     */
    public static final String CURRENT_USER = "Current-User";
    /**
     * JWT加密字符串。
     */
    public static final String JWT_SECRET = "YeSwAy(51()";

}
