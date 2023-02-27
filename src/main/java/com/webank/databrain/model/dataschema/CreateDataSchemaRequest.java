package com.webank.databrain.model.dataschema;

import com.webank.databrain.model.common.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateDataSchemaRequest extends CommonRequest {

    private Long providerId;

    private Long tagId;

    private Long productId;

    private Integer version;

    private Integer visible;

    private String description;

    private String usage;

    private Integer price;

    private Integer type;

    private Integer protocol;

    private String schema;

    private String condition;

    private String uri;

    private LocalDateTime effectTime;

    private LocalDateTime expireTime;

}