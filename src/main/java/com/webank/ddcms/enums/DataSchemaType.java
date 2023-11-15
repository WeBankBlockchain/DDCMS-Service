package com.webank.ddcms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述数据目录可见状态的枚举类
 * @author lql
 * @date 2023/09/04
 */
@AllArgsConstructor
@Getter
@Slf4j
public enum DataSchemaType {
    ONLY_SELF(0),
    VISIBLE(1);

    private final int code;

    public static DataSchemaType getDataType(int code) {
        for (DataSchemaType type : DataSchemaType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
