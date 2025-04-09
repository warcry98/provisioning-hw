package com.voxloud.provisioning.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.ProvisioningService;
import com.voxloud.provisioning.utils.JsonUtils;

@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {
    @Autowired
    private ProvisioningService provisioningService;

    @GetMapping("")
    public String index() {
        return "Getting Started!";
    }

    @GetMapping("/provisioning/{mac}")
    public ResponseEntity<Device> getDeviceByMac(@PathVariable String mac) {
        Optional<Device> optionalDevice = provisioningService.getProvisioningFile(mac);
        if (optionalDevice.isPresent()) {
            if (optionalDevice.get().getOverrideFragment() != null) {
                String overrideFragment = optionalDevice.get().getOverrideFragment();
                Map<String, Object> dataOverride = JsonUtils.parseOverrideFragment(overrideFragment);
                System.out.println(dataOverride.get("domain"));
            }
            return ResponseEntity.ok(optionalDevice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}