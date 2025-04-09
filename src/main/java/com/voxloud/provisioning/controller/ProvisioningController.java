package com.voxloud.provisioning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.ProvisioningService;

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
        return provisioningService.getProvisioningFile(mac).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}