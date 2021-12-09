package cn.woyun.anov.random;

import cn.woyun.anov.consts.PunctuationConst;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 随机数生成工具类。
 *
 * @author xuepeng
 */
public class RandomUtil {

    /**
     * 随机数对象。
     */
    private static final Random random = new Random();

    /**
     * 构造函数。
     */
    private RandomUtil() {
    }

    /**
     * 生成六位数字型字符串。
     *
     * @return 六位数字型字符串。
     */
    public static String getSixDigitsString() {
        int code = random.nextInt(899999) + 100000;
        return String.valueOf(code);
    }

    /**
     * @return 获取一个UUID。
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return 获取一个大写UUID。
     */
    public static String getUpperUUID() {
        return getUUID().toUpperCase();
    }

    /**
     * 批量获取UUID。
     *
     * @param count 指定数量。
     * @return 指定数量的UUID集合。
     */
    public static List<String> getUUIDs(final int count) {
        if (count < 1) {
            throw new IllegalArgumentException("指定数量必须大于0。");
        }
        final List<String> uuids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            uuids.add(getUUID());
        }
        return uuids;
    }

    /**
     * 批量获取大写UUID。
     *
     * @param count 指定数量。
     * @return 指定数量的UUID集合。
     */
    public static List<String> getUpperUUIDs(final int count) {
        final List<String> uuids = getUUIDs(count);
        return uuids.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    /**
     * @return 获取一个32位的UUID（去掉横线）。
     */
    public static String get32UUID() {
        return StringUtils.replace(getUUID(), PunctuationConst.HORIZONTAL_LINE, StringUtils.EMPTY);
    }

    /**
     * @return 获取一个32位的大写UUID（去掉横线）。
     */
    public static String getUpper32UUID() {
        return get32UUID().toUpperCase();
    }

    /**
     * 批量获取32位的UUID（去掉横线）。
     *
     * @param count 指定数量。
     * @return 指定数量的32位UUID集合。
     */
    public static List<String> get32UUIDs(final int count) {
        if (count < 1) {
            throw new IllegalArgumentException("指定数量必须大于0。");
        }
        final List<String> uuids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            uuids.add(get32UUID());
        }
        return uuids;
    }

    /**
     * 批量获取32位的大写UUID（去掉横线）。
     *
     * @param count 指定数量。
     * @return 指定数量的32位大写UUID集合。
     */
    public static List<String> getUpper32UUIDs(final int count) {
        List<String> uuids = get32UUIDs(count);
        return uuids.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    /**
     * 获取12位的字符串。
     *
     * @return 12位字符串。
     */
    public static String get12String() {
        final String str = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
        final StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

}
