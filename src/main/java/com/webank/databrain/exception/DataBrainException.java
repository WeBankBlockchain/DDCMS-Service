package com.webank.databrain.exception;

import com.webank.databrain.enums.CodeEnum;
import lombok.Getter;

@Getter
public class DataBrainException extends RuntimeException{
    private CodeEnum error;
    public DataBrainException(CodeEnum error){
        super(error.getMsg());
    }
}
