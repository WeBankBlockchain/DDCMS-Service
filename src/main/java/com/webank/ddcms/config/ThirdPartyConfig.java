package com.webank.ddcms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方账号配置类
 * @author ashinnotfound
 * @date 2023/09/06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "third-party")
public class ThirdPartyConfig {
    private String githubClientId;
    private String githubClientSecret;
}
