package com.webank.ddcms.bo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccAndComInfoBO {
  private String userName;
  private String did;
  private String privateKey;
  private String accountType;
  private int status;
  private String companyName;
  private String companyDesc;
  private String companyCertType;
  private String companyCertNo;
  private String companyCertFileUri;
  private String companyContact;
  private Timestamp createTime;
}
