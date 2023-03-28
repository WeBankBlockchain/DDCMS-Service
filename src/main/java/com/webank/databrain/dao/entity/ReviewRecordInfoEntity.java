package com.webank.databrain.dao.entity;

import com.webank.databrain.enums.ReviewStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ReviewRecordInfoEntity implements Serializable {

    private Long pkId;

    private Long itemId;

    private Integer itemType;

    private Integer reviewState = ReviewStatus.NotReviewed.ordinal();

    private Integer agreeCount = 0;

    private Integer denyCount = 0;

    private Integer witnessCount;

}
