package cn.woyun.anov.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * 创建Jackson的映射类。
 * 使用枚举单例模式，创建ObjectMapper对象。
 *
 * @author xuepeng
 */
public enum JsonMapper {

    /**
     * JsonMapper单例枚举。
     */
    INSTANCE;

    /**
     * ObjectMapper对象。
     */
    private final ObjectMapper objectMapper;

    /**
     * 创建一个ObjectMapper对象。
     */
    JsonMapper() {
        objectMapper = new ObjectMapper();
        // 反序列化时,遇到未知属性时，不会引起结果序列化失败
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置Java时间序列化
        final JavaTimeModule timeModule = new JavaTimeModule();
        // 获取系统默认时区
        final ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
        // 添加LocalDateTime序列化规则
        timeModule.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeNumber(localDateTime.toInstant(zoneOffset).toEpochMilli());
            }
        });
        // 添加LocalDateTime反序列化规则
        timeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                final long timestamp = jsonParser.getLongValue();
                return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, zoneOffset);
            }
        });
        // 关闭日期将转换为Timestamp的设置
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(timeModule);
    }

    /**
     * @return 获取单例的ObjectMapper对象。
     */
    public ObjectMapper getInstance() {
        return this.objectMapper;
    }

}
