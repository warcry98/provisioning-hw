package com.voxloud.provisioning.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.dto.ConfigurationFile;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.entity.Device.DeviceModel;
import com.voxloud.provisioning.service.ProvisioningService;
import com.voxloud.provisioning.utils.JsonUtils;

@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {
    @Autowired
    private ProvisioningService provisioningService;

    @Autowired
    private ProvisioningProperties provisioningProperties;

    @GetMapping("")
    public String index() {
        return "Getting Started!";
    }

    @GetMapping("/provisioning/{mac}")
    public ResponseEntity<Object> getDeviceByMac(@PathVariable String mac) {
        Optional<Device> optionalDevice = provisioningService.getProvisioningFile(mac);
        if (optionalDevice.isPresent()) {
            Map<String, String> dataOverride = null;
            if (optionalDevice.get().getOverrideFragment() != null) {
                String overrideFragment = optionalDevice.get().getOverrideFragment();
                dataOverride = JsonUtils.parseOverrideFragment(overrideFragment);
                // System.out.println(dataOverride.get("domain"));
            }

            ConfigurationFile configurationFile = new ConfigurationFile();
            configurationFile.setUsername(optionalDevice.get().getUsername());
            configurationFile.setPassword(optionalDevice.get().getPassword());
            configurationFile.setDomain(provisioningProperties.getDomain());
            configurationFile.setPort(provisioningProperties.getPort());
            configurationFile.setCodecs(provisioningProperties.getCodecs());
            if (dataOverride != null) {
                if (dataOverride.get("domain") != null) {
                    configurationFile.setDomain(dataOverride.get("domain"));
                }
                if (dataOverride.get("port") != null) {
                    configurationFile.setPort(dataOverride.get("port"));
                }
                if (dataOverride.get("codecs") != null) {
                    configurationFile.setCodecs(dataOverride.get("codecs"));
                }
                if (dataOverride.get("timeout") != null) {
                    configurationFile.setTimeout(Integer.parseInt(dataOverride.get("timeout")));
                }
            }
            if (optionalDevice.get().getModel() == DeviceModel.CONFERENCE) {
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("username", configurationFile.getUsername());
                jsonResponse.put("password", configurationFile.getPassword());
                jsonResponse.put("domain", configurationFile.getDomain());
                jsonResponse.put("port", configurationFile.getPort());
                jsonResponse.put("codecs", configurationFile.getCodecs().split(","));
                jsonResponse.put("timeout", configurationFile.getTimeout());

                return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse);
            } else if (optionalDevice.get().getModel() == DeviceModel.DESK) {
                String propertyResponse = String.format(
                    "username=%s\npassword=%s\ndomain=%s\nport=%s\ncodecs=%s\ntimeout=%s\n",
                    configurationFile.getUsername(),
                    configurationFile.getPassword(),
                    configurationFile.getDomain(),
                    configurationFile.getPort(),
                    configurationFile.getCodecs(),
                    configurationFile.getTimeout()
                );

                return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(propertyResponse);
            }

        } else {
            return ResponseEntity.notFound().build();
        }
        return null;
    }
}