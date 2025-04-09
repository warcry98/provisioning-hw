package com.voxloud.provisioning.utils;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.voxloud.provisioning.dto.ConfigurationFile;

public class BuildPropertyDesk extends BuildResponse {
    @Override
    public ResponseEntity<Object> BuildResponseEntity(ConfigurationFile configurationFile) {
        String propertyResponse = String.format(
                "username=%s\npassword=%s\ndomain=%s\nport=%s\ncodecs=%s\ntimeout=%s\n",
                configurationFile.getUsername(),
                configurationFile.getPassword(),
                configurationFile.getDomain(),
                configurationFile.getPort(),
                configurationFile.getCodecs(),
                configurationFile.getTimeout()
            );
        
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(propertyResponse);
    }
}
