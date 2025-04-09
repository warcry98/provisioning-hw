package com.voxloud.provisioning.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.voxloud.provisioning.dto.ConfigurationFile;

public class BuildJsonConference extends BuildResponse {
    @Override
    public ResponseEntity<Object> BuildResponseEntity(ConfigurationFile configurationFile) {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("username", configurationFile.getUsername());
        jsonResponse.put("password", configurationFile.getPassword());
        jsonResponse.put("domain", configurationFile.getDomain());
        jsonResponse.put("port", configurationFile.getPort());
        jsonResponse.put("codecs", configurationFile.getCodecs().split(","));
        jsonResponse.put("timeout", configurationFile.getTimeout());
        
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
    }
}
