package com.webank.ddcms.vo.request.dataschema;

import com.webank.ddcms.aspect.JsonValid;
import com.webank.ddcms.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateDataSchemaRequest extends CommonRequest {

  @NotBlank(message = "目录名不能为空.")
  private String dataSchemaName;

  private Long productId;

  private List<String> tagNameList;
  /** 版本号 */
  private Integer version = 1;

  private Integer visible;

  @NotBlank(message = "目录描述不能为空.")
  private String dataSchemaDesc;

  /** 用途 */
  private String dataSchemaUsage;

  /** 价格 */
  private Integer price;

  private Integer dataFormat;

  /** 类型，HTTP-0，HTTPS-1，SFTP-2... */
  private Integer dataProtocol;

  /** 内容格式 */
  @JsonValid(message = "内容格式需要为Json格式")
  private String contentSchema;

  /** 数据的查询条件定义 */
  @JsonValid(message = "查询条件格式需要为Json格式")
  private String accessCondition;

  /** 数据访问连接 */
  private String uri;

  /** 生效时间 */
  private Date effectTime;

  /** 失效时间 */
  private Date expireTime;
}
