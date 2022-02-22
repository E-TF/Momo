package com.project.momo.common.annotation;

import com.project.momo.common.annotation.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(max = 20)
@NotBlank
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneNumberValidator.class})
public @interface PhoneNumber {
    String message() default "옳바르지 않은 전화번호 형식 입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
