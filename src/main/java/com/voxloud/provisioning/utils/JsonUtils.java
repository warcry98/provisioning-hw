package com.voxloud.provisioning.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> parseOverrideFragment(String overrideFragment) {
        try {
            return objectMapper.readValue(overrideFragment, new TypeReference<Map<String, Object>>() {
                
            });
        } catch (Exception firstException) {
            try {
                Properties props = new Properties();
                props.load(new StringReader(overrideFragment));
                Map<String, Object> map = new HashMap<>();
                for (String key : props.stringPropertyNames()) {
                    map.put(key, props.getProperty(key));
                }
                return map;
            } catch (Exception secondException) {
                secondException.printStackTrace();
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put("error", "Unrecognized override fragment format");
                return errorMap;
            }
        }
    }
}
