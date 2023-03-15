package com.webank.databrain.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.databrain.enums.CodeEnum;
import com.webank.databrain.vo.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @Autowired
    private ObjectMapper objectMapper;
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse handleMethodArgumentNotValidException(HttpServletRequest req, Exception e) throws JsonProcessingException {
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException)e;
        FieldError fe = exception.getFieldError();
        String debugRtnMsg = CodeEnum.PARAMETER_ERROR.getMsg() + ":" + fe.getDefaultMessage();
        CommonResponse response = CommonResponse.error(CodeEnum.PARAMETER_ERROR.getCode(), debugRtnMsg);
        log.info("response : {}", objectMapper.writeValueAsString(response));
        return response;
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public CommonResponse handleSQLDuplicateKeyException(HttpServletRequest req, Exception e) throws JsonProcessingException {
        CommonResponse response = CommonResponse.error(CodeEnum.SQL_DUPLICATE_RECORD);
        log.info("response : {}", objectMapper.writeValueAsString(response));
        return response;
    }
}
