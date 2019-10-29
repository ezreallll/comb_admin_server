package com.shp.comb.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.io.IOException;
import java.util.*;


public class JSonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSonUtil.class);

    private static ThreadLocal<StringBuilder> local = new ThreadLocal<StringBuilder>();
    private static Map<Class, BeanInfo> beanInfoMap = Collections.synchronizedMap(new WeakHashMap<Class, BeanInfo>());
    private static final ObjectMapper mapper = new ObjectMapper();  //生成json

    static {
        mapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //手动设置时区
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    }


    public static final String toJson(Map<String, String> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final String toJson(Object ob) {
        try {
            return mapper.writeValueAsString(ob);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final String toJsonWithObject(Map<String, Object> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final Map<String, Object> toMap(String json) {
        try {
            return mapper.readValue(json, HashMap.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final ArrayList<Object> toList(String json) {
        try {
            return mapper.readValue(json, ArrayList.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final Object toObject(String json) {
        try {
            return mapper.readValue(json, Object.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final <T> T toObject(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final <T> T convertObject(Object bean, TypeReference<T> type) {
        try {
            return mapper.convertValue(bean, type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final <T> List<T> toObjectList(String json, TypeReference<? extends Collection<T>> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final <K, V> Map<K, V> toObjectMap(String json, TypeReference<? extends Map<K, V>> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final <T> T toObject(String json, Class<T> tclass) {
        try {
            return mapper.readValue(json, tclass);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static final String toJson(List<String> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);  //logger exception
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
    }

}