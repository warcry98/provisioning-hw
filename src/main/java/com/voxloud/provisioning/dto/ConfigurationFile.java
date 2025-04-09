package com.voxloud.provisioning.dto;

import lombok.Data;

@Data
public class ConfigurationFile {
    private String username;
    private String password;
    private String domain;
    private String port;
    private String codecs;
    private int timeout;
}
