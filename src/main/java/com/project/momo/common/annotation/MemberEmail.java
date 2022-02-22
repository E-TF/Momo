package com.project.momo.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Size(max = 255)
@Email
@NotEmpty
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface MemberEmail {
    String message() default "올바른 형식의 이메일 주소여야 하며 최대 255자 입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
