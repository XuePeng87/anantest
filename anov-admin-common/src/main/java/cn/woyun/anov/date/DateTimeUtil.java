package cn.woyun.anov.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * 日期时间工具类。
 *
 * @author xuepeng
 */
public class DateTimeUtil {

    /**
     * 构造函数。
     */
    private DateTimeUtil() {
    }

    /**
     * @return 使用系统默认时区，获取从现在到明天的秒数。
     */
    public static long getSecoudFromNowToTomorrow() {
        return getSecoudFromNowToTomorrow(OffsetDateTime.now().getOffset());
    }

    /**
     * 自定义时区，获取从现在到明天的秒数。
     *
     * @param zoneOffset 时区。
     * @return 从现在到明天的秒数。
     */
    public static long getSecoudFromNowToTomorrow(final ZoneOffset zoneOffset) {
        long now = LocalDateTime.now().toEpochSecond(zoneOffset);
        long next = LocalDate.now().plusDays(1).atStartOfDay().toEpochSecond(zoneOffset);
        return next - now;
    }

}
