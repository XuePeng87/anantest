package cn.woyun.anov.gateway.dev.consts;

/**
 * 大屏使用的常量。
 *
 * @author xuepeng
 */
public class DevScreenConsts {

    /**
     * 构造函数。
     */
    private DevScreenConsts() {
    }

    /**
     * 令牌。
     */
    public static final String CLIENT_TOKEN = "X-Anov-Token";

    /**
     * 客户端唯一标识。
     */
    public static final String CLIENT_CODE = "X-Anov-Client-Code";

    /**
     * 客户端目标的唯一标识。
     */
    public static final String CLIENT_TARGET_CODE = "X-Anov-Clinet-Target-Code";

    /**
     * 客户端类型。
     */
    public static final String CLIENT_TYPE = "X-Anov-Client-Type";

    /**
     * 框架版本。
     */
    public static final String CLIENT_VERSION = "X-Anov-Version";

    /**
     * 内核版本。
     */
    public static final String CLIENT_CORE_VERSION = "X-Anov-Core-Version";

    /**
     * 客户端大屏等级。
     */
    public static final String CLIENT_LEVEL = "X-Anov-Level";

    /**
     * 客户端大屏开发环境。
     */
    public static final String CLIENT_IS_DEBUG = "X-Anov-Debug";

    /**
     * 客户端大屏开发环境。
     */
    public static final String CLIENT_DIR = "X-Anov-Dir";

    /**
     * 统计信息。
     */
    public static final String GA = "GA";

    /**
     * 客户端信息。
     */
    public static final String USER_AGENT = "User-Agent";

}
