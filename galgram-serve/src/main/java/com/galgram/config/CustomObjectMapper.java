package com.galgram.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 序列化配置
 * 1. 序列号与反序列化，统一datatime格式
 * 2. 序列化时将Long转为String
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Component
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        // 在构造函数中添加您的自定义配置
        // 例如：启用/禁用特定的序列化/反序列化特性，注册模块等
        // 以下是一些示例配置：
        // 启用缩进输出
        // this.enable(SerializationFeature.INDENT_OUTPUT);
        // 注册Java 8日期时间模块
        this.registerModule(new JavaTimeModule());
        // 创建一个自定义模块
        SimpleModule customModule = new SimpleModule();
        // 在自定义模块中添加对 LocalDateTime 类型的序列化和反序列化的处理
        customModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        customModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        customModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        customModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        customModule.addSerializer(Long.class, new LongSerializer());
//        customModule.addDeserializer(Long.class, new LongDeserializer());
        // 将自定义模块注册到 ObjectMapper 中
        registerModule(customModule);
        // 禁用日期时间序列化为时间戳
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // 自定义 LocalDateTime 的序列化器
    private static class LocalDateTimeSerializer extends com.fasterxml.jackson.databind.JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }

    // 自定义 LocalDateTime 的反序列化器
    private static class LocalDateTimeDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws java.io.IOException, com.fasterxml.jackson.core.JsonProcessingException {
            return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    private static class LocalDateSerializer extends com.fasterxml.jackson.databind.JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
            gen.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
    }

    // 自定义 LocalDateTime 的反序列化器
    private static class LocalDateDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws java.io.IOException, com.fasterxml.jackson.core.JsonProcessingException {
            return LocalDate.parse(p.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    private static class LongSerializer extends com.fasterxml.jackson.databind.JsonSerializer<Long> {
        @Override
        public void serialize(Long value, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
            gen.writeString(value.toString());
        }
    }

    private static class LongDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<Long> {
        @Override
        public Long deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws java.io.IOException, com.fasterxml.jackson.core.JsonProcessingException {
            return Long.valueOf(p.getValueAsString());
        }
    }
}
