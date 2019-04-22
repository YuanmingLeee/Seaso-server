package com.seaso.seaso.common.web.validation.validatior;

import com.seaso.seaso.common.web.validation.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, Object> {

    private static final Pattern pattern = Pattern.compile("[\\s!@#$%&_.,;]");

    @Override
    public void initialize(Username constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String)
            return !pattern.matcher((String) value).find();
        return false;
    }
}
