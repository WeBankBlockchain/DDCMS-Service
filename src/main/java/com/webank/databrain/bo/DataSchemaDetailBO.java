package com.webank.databrain.bo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class DataSchemaDetailBO {
    private Long schemaId;

    /**
     * 数据目录名称
     */
    private String dataSchemaName;

    /**
     * 提供方外键id
     */
    private Long providerId;

    private String providerName;

    private List<String> tagNameList;


    /**
     * 产品外键ID
     */
    private Long productId;

    private String productName;


    /**
     * 版本号
     */
    private Integer version;

    /**
     * 是否可见
     */
    private Integer visible;

    /**
     * 描述
     */
    private String dataSchemaDesc;

    /**
     * 用途
     */
    private String dataSchemaUsage;

    /**
     * 价格
     */
    private Integer price;


    /**
     * 审核状态
     */
    private Integer status;

    private Integer favTag;

    /**
     * 审核时间
     */
    private Timestamp reviewTime;

    private Timestamp createTime;

    private Integer agreeCount;

    private Integer denyCount;

    private Integer witnessCount;
}