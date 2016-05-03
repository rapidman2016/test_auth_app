package com.test.websocket.auth.api.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.test.websocket.auth.api.ProtocolConstants.*;

/**
 * Created by timur on 01.05.16.
 */
public class JsonSerialyzer {

    //thread-safe if not configurations method is invoked
    public static ObjectMapper mapper = new ObjectMapper();

    static {
        setupObjectMapper(mapper);
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readJsonValue(String json, Class<T> klass) throws JsonParseException {
        try {
            return mapper.readValue(json, klass);
        } catch (JsonParseException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean containsField(String json, String field) {
        try {
            JsonNode node = mapper.readTree(json);
            return node.has(field);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readJsonValue(String json, JavaType javaType) {
        try {
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setupObjectMapper(ObjectMapper objectMapper) {
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        final DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        objectMapper.setDateFormat(df);
    }

    public static JsonNode toJsonTree(String json) throws IOException {
        return mapper.readTree(json);
    }
}
