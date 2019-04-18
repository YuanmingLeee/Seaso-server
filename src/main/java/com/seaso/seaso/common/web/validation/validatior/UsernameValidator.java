package com.seaso.seaso.common.web.validation.validatior;

import com.seaso.seaso.common.web.validation.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, Object> {

    private static final String pattern = "[\\s!@#$%&_.,;]|^$";

    @Override
    public void initialize(Username constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String)
            return !((String) value).matches(pattern);
        return false;
    }
}
