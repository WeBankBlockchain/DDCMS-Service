package com.webank.databrain.vo.request.dataschema;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CreateDataSchemaRequest {


    private String did;

    @NotBlank(message = "dataSchemaName不能为空.")
    private String dataSchemaName;

    @NotBlank(message = "providerId不能为空.")
    private Long providerId;

    @NotBlank(message = "providerGId不能为空.")
    private String providerGId;

    private String providerName;

    @NotBlank(message = "productId不能为空.")
    private Long productId;

    @NotBlank(message = "productGId不能为空.")
    private String productGId;

    private String productName;

    @NotBlank(message = "tagId不能为空.")
    private Long tagId;

    /**
     * 版本号
     */
    private Integer version;

    @NotBlank(message = "visible不能为空.")
    private Integer visible;

    @NotBlank(message = "dataSchemaDesc不能为空.")
    private String dataSchemaDesc;

    /**
     * 用途
     */
    private String dataSchemaUsage;

    /**
     * 价格
     */
    private Integer price;

    private Date createTime;

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
    private Date effectTime;

    /**
     * 失效时间
     */
    private Date expireTime;
}
