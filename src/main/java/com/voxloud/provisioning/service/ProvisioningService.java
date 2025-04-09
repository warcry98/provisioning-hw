package com.voxloud.provisioning.service;

import java.util.Optional;

import com.voxloud.provisioning.entity.Device;

public interface ProvisioningService {

    Optional<Device> getProvisioningFile(String macAddress);
}
