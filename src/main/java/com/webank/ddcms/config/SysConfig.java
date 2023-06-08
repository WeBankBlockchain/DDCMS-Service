package com.webank.ddcms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "system")
@Configuration
@Data
public class SysConfig {

  private String bcosCfg;

  private String bcosGroupId;

  private int cryptoConfig;

  private String adminAccount;

  private String adminPassword;

  private String adminPrivateKey;

  private String adminCompany;

  @NestedConfigurationProperty private ContractConfig contractConfig;

  @NestedConfigurationProperty private FileConfig fileConfig;

  @Data
  public static class ContractConfig {
    private String accountContract;
    private String productContract;
    private String dataSchemaContract;
  }

  @Data
  public static class FileConfig {
    private String fileDir;
  }
}
