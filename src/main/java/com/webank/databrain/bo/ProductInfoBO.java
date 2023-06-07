package com.webank.databrain.bo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductInfoBO {

  private Long productId;

  /** 产品名称 */
  private String productName;

  /** 提供方外键ID */
  private Long providerId;

  /** 产品详情 */
  private String productDesc;

  /** 审核状态 */
  private Integer status;

  /** 审核时间 */
  private Timestamp reviewTime;

  private Timestamp createTime;

  private String companyName;

  private Integer agreeCount;

  private Integer denyCount;

  private Integer witnessCount;
}
