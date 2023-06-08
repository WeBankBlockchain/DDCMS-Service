package com.webank.ddcms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataSchemaWithAccessBO extends DataSchemaDetailBO {

  private Long accessId;

  /** 数据目录外键id */

  /** 类型，json-0，xml-1，doc-2，pic-3... */
  private Integer dataFormat;

  /** 类型，HTTP-0，HTTPS-1，SFTP-2... */
  private Integer dataProtocol;

  /** 内容格式 */
  private String contentSchema;

  /** 数据的查询条件定义 */
  private String accessCondition;

  /** 数据访问连接 */
  private String uri;

  /** 生效时间 */
  private Timestamp effectTime;

  /** 失效时间 */
  private Timestamp expireTime;
}
