package com.webank.databrain.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class AccountInfoEntity implements Serializable {
    private Long pkId;
    private String userName;
    private String did;
    private Integer accountType;
    private String privateKey;
    private String password;
    private Integer status;
    private Date reviewTime;
    private Date createTime;
    private Date updateTime;
}
