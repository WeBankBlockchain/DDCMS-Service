package com.webank.ddcms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public enum AccountType {
  COMPANY(1, "COMPANY"),
  WITNESS(2, "WITNESS"),
  ADMIN(3, "ADMIN");

  private final int roleKey;
  private final String roleName;

  public static AccountType getAccountType(int roleKey) {
    for (AccountType type : AccountType.values()) {
      if (type.roleKey == roleKey) {
        return type;
      }
    }
    log.error("{} can't be converted.", roleKey);
    return null;
  }
}
