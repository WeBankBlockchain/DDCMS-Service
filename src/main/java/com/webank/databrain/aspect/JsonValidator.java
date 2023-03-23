package com.webank.databrain.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JsonValidator implements ConstraintValidator<JsonValid, Object> {


    private static ObjectMapper JSON_VALID =  new ObjectMapper();


    public static boolean isValidJson(String json) {
        try {
            JSON_VALID.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return isValidJson((String) value);
    }
}
