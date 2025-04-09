package com.voxloud.provisioning.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "provisioning")
public class ProvisioningProperties {
    private String domain;
    private String port;
    private String codecs;
}