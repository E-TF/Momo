package com.project.momo.common.annotation.validator;

import com.project.momo.common.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private final Pattern pattern = Pattern.compile("^\\+\\d{2,3}-\\d{2}-\\d{3,4}-\\d{4}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return pattern.matcher(value).matches();
    }
}
