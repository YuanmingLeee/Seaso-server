package com.seaso.seaso.common.web.validation.validatior;

import com.seaso.seaso.common.web.validation.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, Object> {

    private static final String pattern = "(?=^\\S{8,16}$)(?=.*[\\d])(?=.*[a-zA-Z])";

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String) {
            return ((String) value).matches(pattern);
        }
        return false;
    }
}
