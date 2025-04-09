package com.voxloud.provisioning.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {
    @Autowired
    DeviceRepository deviceRepository;

    public Optional<Device> getProvisioningFile(String macAddress) {
        return deviceRepository.findByMacAddress(macAddress);
    }
}
