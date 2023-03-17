package com.webank.databrain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

    private List<String> permitAllApiList;
    private String anonymousApi;

    @NestedConfigurationProperty
    private AuthConfig.RoleAuth roleAuth;

    @Data
    public static class RoleAuth {
        private List<String> companyAuth;
        private List<String> witnessAuth;
        private List<String> adminAuth;
    }
}