package com.webank.databrain.advice;

import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.vo.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse handleMethodArgumentNotValidException(HttpServletRequest req, Exception e){
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException)e;
        FieldError fe = exception.getFieldError();
        String debugRtnMsg = CodeEnum.PARAMETER_ERROR.getMsg() + ":" + fe.getDefaultMessage();
        return CommonResponse.error(CodeEnum.PARAMETER_ERROR.getCode(), debugRtnMsg);
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public CommonResponse handleSQLDuplicateRecordException(HttpServletRequest req, Exception e){
        if(e instanceof SQLIntegrityConstraintViolationException){
            return CommonResponse.error(CodeEnum.SQL_DUPLICATE_RECORD);
        }
        return null;
    }
}
