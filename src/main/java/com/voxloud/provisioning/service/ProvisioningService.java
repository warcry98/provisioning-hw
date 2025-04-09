package com.voxloud.provisioning.service;

import org.springframework.http.ResponseEntity;

public interface ProvisioningService {

    public ResponseEntity<Object> getProvisioningFile(String macAddress);
}
