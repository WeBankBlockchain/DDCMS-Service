package com.webank.ddcms.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SchemaFavoriteInfoEntity implements Serializable {

  private Long pkId;

  private Long schemaId;

  private Long accountId;
}
