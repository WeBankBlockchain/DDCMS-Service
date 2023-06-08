package com.webank.databrain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public enum ReviewItemType {
  Product(1),
  Schema(2);

  private final int code;

  public static ReviewItemType getAccountType(int code) {
    for (ReviewItemType type : ReviewItemType.values()) {
      if (type.code == code) {
        return type;
      }
    }
    return null;
  }
}
