package com.webank.ddcms.aspect;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {JsonValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonValid {
  String message() default "参数不是Json格式";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
