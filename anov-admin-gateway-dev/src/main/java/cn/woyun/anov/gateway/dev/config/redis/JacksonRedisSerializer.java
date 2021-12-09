package cn.woyun.anov.gateway.dev.config.redis;

import cn.woyun.anov.bean.BeanUtil;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 使用Alibaba的FastJson作为Json序列化的工具。
 *
 * @param <T>
 * @author xuepeng
 */
public class JacksonRedisSerializer<T> implements RedisSerializer<T> {

    /**
     * 默认的转换编码。
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName(StandardCharsets.UTF_8.name());
    /**
     * 要转换的类的Class对象。
     */
    private final Class<T> clazz;

    /**
     * 构造函数。
     *
     * @param clazz 设置要序列化的对象的Class对象。
     */
    public JacksonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 序列化对象。
     *
     * @param object 要被序列化的对象。
     * @return 序列化成字节数组。
     */
    @Override
    public byte[] serialize(T object) {
        if (object == null) {
            return new byte[0];
        }
        return BeanUtil.getObjToStr(object).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 反序列化对象。
     *
     * @param bytes 字节数组。
     * @return 要序列化的对象。
     */
    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return BeanUtil.getStrToObj(str, clazz);
    }

}
