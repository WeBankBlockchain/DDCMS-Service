package com.webank.ddcms.exception;

import com.webank.ddcms.enums.CodeEnum;
import lombok.Getter;

@Getter
public class DDCMSException extends RuntimeException {
  private CodeEnum error;

  public DDCMSException(CodeEnum error) {
    super(error.getMsg());
  }
}
