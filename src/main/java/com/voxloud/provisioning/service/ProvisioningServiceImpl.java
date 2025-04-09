package com.voxloud.provisioning.service;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.dto.ConfigurationFile;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.entity.Device.DeviceModel;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.utils.BuildJsonConference;
import com.voxloud.provisioning.utils.BuildPropertyDesk;
import com.voxloud.provisioning.utils.JsonUtils;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    private ProvisioningProperties provisioningProperties;

    public ResponseEntity<Object> getProvisioningFile(String macAddress) {
        Optional<Device> optionalDevice = deviceRepository.findByMacAddress(macAddress);
        if (optionalDevice.isPresent()) {
            Map<String, String> dataOverride = null;
            if (optionalDevice.get().getOverrideFragment() != null) {
                String overrideFragment = optionalDevice.get().getOverrideFragment();
                dataOverride = JsonUtils.parseOverrideFragment(overrideFragment);
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
                return new BuildJsonConference().BuildResponseEntity(configurationFile);
            } else if (optionalDevice.get().getModel() == DeviceModel.DESK) {
                return new BuildPropertyDesk().BuildResponseEntity(configurationFile);
            }

        } else {
            return ResponseEntity.notFound().build();
        }
        return null;
    }
}
