package com.voxloud.provisioning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.voxloud.provisioning.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<com.voxloud.provisioning.entity.Device, String> {

    Optional<Device> findByMacAddress(String macAddress);
}
