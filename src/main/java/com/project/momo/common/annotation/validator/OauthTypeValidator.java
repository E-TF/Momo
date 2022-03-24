package com.project.momo.common.annotation.validator;

import com.project.momo.security.consts.OauthType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OauthTypeValidator implements ConstraintValidator<com.project.momo.common.annotation.OauthType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        for (OauthType oauthType : OauthType.values()) {
            if(oauthType.getValue().equals(value)) return true;
        }
        return false;
    }
}
