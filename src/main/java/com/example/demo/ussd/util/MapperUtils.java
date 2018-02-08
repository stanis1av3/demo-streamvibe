package com.example.demo.ussd.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Pavlovskii-pc on 07/12/2017.
 */
public class MapperUtils {

    static ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();//.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static <T> T toObject(Class<T> t, String json) {
        try {
            return mapper.readValue(json, t);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toJson(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("cant serialize to json");
        }
    }

}
