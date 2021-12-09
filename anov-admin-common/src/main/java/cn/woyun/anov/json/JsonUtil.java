package cn.woyun.anov.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

/**
 * Json对象转换工具类。
 * 使用Jackson进行对象与Json字符串之间的转换。
 *
 * @author xuepeng
 */
public class JsonUtil {

    /**
     * 构造函数。
     */
    private JsonUtil() {
    }

    /**
     * 创建Jackson的映射对象。
     */
    private static final ObjectMapper MAPPER = JsonMapper.INSTANCE.getInstance();

    /**
     * 将一个对象转换成Json字符串。
     *
     * @param obj 要转换的对象。
     * @param <T> 要转换的对象的类型。
     * @return 对象的Json字符串。
     */
    public static <T> String convertObj2Str(final T obj) {
        String result;
        if (Objects.isNull(obj)) {
            throw new NullPointerException("要转换的对象不能为空。");
        }
        // 判断要转换的对象是否是字符串
        if (obj instanceof String) {
            // 如果是，则直接返回一个新的字符串。
            result = String.valueOf(obj);
        } else {
            // 如果不是，则通过Jsckson转换成json字符串。
            try {
                result = MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("对象转换字符串失败。", e);
            }
        }
        return result;
    }

    /**
     * 将一个Json字符串转换成对象。
     *
     * @param str   Json字符串。
     * @param clazz 要转换的对象的class对象。
     * @param <T>   要转换的对象的类型。
     * @return 转换后的对象。
     */
    public static <T> T convertStr2Obj(final String str, Class<T> clazz) {
        try {
            return MAPPER.readValue(str, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("字符串转换对象失败。", e);
        }
    }

}
