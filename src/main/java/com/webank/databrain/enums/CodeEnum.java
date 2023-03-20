package com.webank.databrain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public enum CodeEnum {
    TRANSACTION_SUCCESS(0, "操作成功"),
    PARAMETER_ERROR(100, "输入有误，请重新输入"),

    USER_NOT_EXISTS(201, "账户不存在"),
    PWD_NOT_RIGHT(202, "账户密码不正确"),
    ACCOUNT_HAS_APPROVED(203, "账户已审核"),

    PRODUCT_NOT_EXISTS(400, "产品不存在"),

    UN_AUTHORIZED(401, "用户认证失败"),
    FORBIDDEN(403, "权限不足"),

    SCHEMA_NOT_EXISTS(500, "产品不存在"),

    FILE_NOT_EXIST(800, "文件不存在"),
    BLOCKCHAIN_TX_ERROR(801, "区块链交易执行失败"),

    LOGIN_FAILED(802, "用户名或密码错误"),
    TX_TIME_OUT(901, "系统超时"),
    SQL_DUPLICATE_RECORD(902, "记录已存在"),
    UNKNOWN_ERROR(9999, "系统错误");

    private int code;
    private String msg;

    public static CodeEnum getCodeEnum(int code) {
        for (CodeEnum type : CodeEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        log.error("{} can't be converted.", code);
        return UNKNOWN_ERROR;
    }
}
