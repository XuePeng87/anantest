package cn.woyun.anov.sdk.dev.entity;

import cn.hutool.core.util.URLUtil;
import cn.woyun.anov.consts.PunctuationConst;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 大屏分析数据。
 *
 * @author xuepeng
 */
@Data
public class DevScreenAnalytics {

    /**
     * 构造函数。
     *
     * @param analytics 分析信息。
     */
    public DevScreenAnalytics(final String analytics) {
        // 拆分analytics字符串
        final String[] item = StringUtils.isNotBlank(analytics) ?
                analytics.split("\\|")[0].split(PunctuationConst.AND) : new String[0];
        this.domain = item.length > 0 ? getAnalyticsValue(item[0]) : StringUtils.EMPTY;
        this.url = item.length > 1 ? URLUtil.decode(getAnalyticsValue(item[1])) : StringUtils.EMPTY;
        this.title = item.length > 2 ? URLUtil.decode(getAnalyticsValue(item[2])) : StringUtils.EMPTY;
        this.referer = item.length > 3 ? URLUtil.decode(getAnalyticsValue(item[3])) : StringUtils.EMPTY;
        this.height = item.length > 4 ? getAnalyticsValue(item[4]) : StringUtils.EMPTY;
        this.width = item.length > 5 ? getAnalyticsValue(item[5]) : StringUtils.EMPTY;
        this.color = item.length > 6 ? getAnalyticsValue(item[6]) : StringUtils.EMPTY;
        this.language = item.length > 7 ? getAnalyticsValue(item[7]) : StringUtils.EMPTY;
        this.gpuName = item.length > 8 ? getAnalyticsValue(item[8]) : StringUtils.EMPTY;
        this.gpuFamily = item.length > 9 ? getAnalyticsValue(item[9]) : StringUtils.EMPTY;
        this.memTotal = item.length > 10 ? getAnalyticsValue(item[10]) : StringUtils.EMPTY;
        this.memUsed = item.length > 11 ? getAnalyticsValue(item[11]) : StringUtils.EMPTY;
        this.fps = item.length > 12 ? getAnalyticsValue(item[12]) : StringUtils.EMPTY;
    }

    private String getAnalyticsValue(final String item) {
        if (StringUtils.isBlank(item)) {
            return StringUtils.EMPTY;
        }
        final String[] dict = StringUtils.split(item, "=");
        if (dict.length > 1) {
            return dict[1];
        }
        return StringUtils.EMPTY;
    }

    /**
     * 域名。
     */
    private final String domain;

    /**
     * 当前页面。
     */
    private final String url;

    /**
     * 大屏标题。
     */
    private final String title;

    /**
     * 来源。
     */
    private String referer;

    /**
     * 屏幕分辨率高度。
     */
    private String height;

    /**
     * 屏幕分辨率宽度。
     */
    private String width;

    /**
     * 屏幕色彩。
     */
    private String color;

    /**
     * 屏幕语言。
     */
    private String language;

    /**
     * 显卡名称。
     */
    private String gpuName;

    /**
     * 显卡厂家。
     */
    private String gpuFamily;

    /**
     * 浏览器总内存。
     */
    private String memTotal;

    /**
     * 浏览器剩余内存。
     */
    private String memUsed;

    /**
     * 每秒传输帧数。
     */
    private String fps;

}
