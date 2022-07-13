package com.project.momo.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Pattern(regexp = "^\\+\\d{2,3}-\\d{2}-\\d{3,4}-\\d{4}$", message = "올바르지 않은 전화번호 형식입니다.")
@Constraint(validatedBy = {})
public @interface PhoneNumber {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
