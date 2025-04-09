package com.voxloud.provisioning.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.voxloud.provisioning.dto.ConfigurationFile;

public class BuildResponse {
    public ResponseEntity<Object> BuildResponseEntity(ConfigurationFile configurationFile) {
        Map<String, Object> jsonResponse = new HashMap<>();
        
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
    }
}
