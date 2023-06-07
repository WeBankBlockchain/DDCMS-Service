package com.webank.databrain.bo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccAndPersonInfoBO {
  private String userName;
  private String did;
  private String privateKey;
  private int status;
  private String personName;
  private String personContact;
  private String personEmail;
  private String personCertType;
  private String personCertNo;
  private Timestamp createTime;
}
