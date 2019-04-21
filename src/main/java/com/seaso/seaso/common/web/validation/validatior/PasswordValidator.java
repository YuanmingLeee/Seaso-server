package com.seaso.seaso.common.web.validation.validatior;

import com.seaso.seaso.common.web.validation.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, Object> {

    private static final Pattern pattern = Pattern.compile("(?=^\\S{8,16}$)(?=.*[\\d])(?=.*[a-zA-Z])");

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String) {
            return pattern.matcher((String) value).find();
        }
        return false;
    }
}
