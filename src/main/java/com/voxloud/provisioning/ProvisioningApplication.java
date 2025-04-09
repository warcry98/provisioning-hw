package com.voxloud.provisioning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.voxloud.provisioning.config.ProvisioningProperties;

@SpringBootApplication
@EnableConfigurationProperties(ProvisioningProperties.class)
public class ProvisioningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProvisioningApplication.class, args);
    }

}