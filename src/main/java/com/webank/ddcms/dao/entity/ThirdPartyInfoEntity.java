package com.webank.ddcms.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 第三方账号信息实体类
 * @author ashinnotfound
 * @date 2023/09/06
 */
@Data
@Accessors(chain = true)
public class ThirdPartyInfoEntity {
    private String did;
    private Long githubId;
}
