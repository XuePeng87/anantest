package cn.woyun.anov.gateway.dev.config.redis;

import cn.woyun.anov.bean.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义StringRedisSerializer列化器。
 * 原生的StringRedisSerializer在使用@Cacheable注解时，当key不是String类型会发生错误。
 * 因为原生的StringRedisSerializer只支持String。
 *
 * @author xuepeng
 */
public class StringRedisSerializer implements RedisSerializer<Object> {

    /**
     * 默认的转换编码。
     */
    private final Charset charset;
    /**
     * 反斜杠符号。
     */
    private static final String TARGET = "\"";
    /**
     * 空格符号。
     */
    private static final String REPLACEMENT = "";

    /**
     * 构造函数。
     */
    StringRedisSerializer() {
        this(Charset.forName(StandardCharsets.UTF_8.name()));
    }

    /**
     * 构造函数。
     *
     * @param charset 转换编码。
     */
    StringRedisSerializer(Charset charset) {
        this.charset = charset;
    }

    /**
     * 序列化对象。
     *
     * @param object 对象。
     * @return 序列化后的byte数组。
     */
    @Override
    public byte[] serialize(Object object) {
        String string = BeanUtil.getObjToStr(object);
        if (StringUtils.isEmpty(string)) {
            return new byte[0];
        }
        string = string.replace(TARGET, REPLACEMENT);
        return string.getBytes(charset);
    }

    /**
     * 反序列化对象。
     *
     * @param bytes byte数组。
     * @return 序列化后的对象。
     */
    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes != null) {
            return new String(bytes, charset);
        }
        return null;
    }

}
