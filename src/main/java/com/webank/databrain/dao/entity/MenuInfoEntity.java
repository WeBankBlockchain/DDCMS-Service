package com.webank.databrain.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class MenuInfoEntity implements Serializable {
    private long pkId;
    private int menuId;
    private String menuName;
    private int parentId;
    private String menuUrl;
    private int menuRole;
    private Date createTime;
    private Date updateTime;
}
