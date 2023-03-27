package com.webank.databrain.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ReviewRecordInfoEntity implements Serializable {

    private Long pkId;

    private Long itemId;

    private Integer itemType;

    private Integer reviewState;

    private Integer agreeCount;

    private Integer denyCount;

    private Integer witnessCount;

}
