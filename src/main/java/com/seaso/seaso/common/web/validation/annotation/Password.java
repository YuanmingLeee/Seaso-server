package com.seaso.seaso.common.web.validation.annotation;

import com.seaso.seaso.common.web.validation.validatior.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {

    String message() default "Password should contain 8 - 16 characters, 1 digits, 1 alphabet, and no whitespace.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
