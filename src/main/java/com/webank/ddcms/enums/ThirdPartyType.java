package com.webank.ddcms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 第三方账号类型
 *
 * @author ashinnotfound
 * @date 2023/08/31
 */
@AllArgsConstructor
@Getter
public enum ThirdPartyType {
    github(1);
    
    private final int code;

    public static ThirdPartyType getThirdPartyType(int code) {
        for (ThirdPartyType type: ThirdPartyType.values()){
            if (type.code == code){
                return type;
            }
        }
        return null;
    }
}
