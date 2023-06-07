package com.webank.databrain.vo.request.dataschema;

import com.webank.databrain.vo.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateDataSchemaRequest extends CommonRequest {


    @NotBlank(message = "目录ID不能为空.")
    private Long schemaId;

    private String dataSchemaName;

    private List<String> tagNameList;
    /**
     * 版本号
     */
    private Integer version;

    private Integer visible;

    private String dataSchemaDesc;

    /**
     * 用途
     */
    private String dataSchemaUsage;

    /**
     * 价格
     */
    private Integer price;


    private Long accessId;

    private Integer dataFormat;

    /**
     * 类型，HTTP-0，HTTPS-1，SFTP-2...
     */
    private Integer dataProtocol;

    /**
     * 内容格式
     */
    private String contentSchema;

    /**
     * 数据的查询条件定义
     */
    private String accessCondition;

    /**
     * 数据访问连接
     */
    private String uri;

    /**
     * 生效时间
     */
    private Timestamp effectTime;

    /**
     * 失效时间
     */
    private Timestamp expireTime;

}
