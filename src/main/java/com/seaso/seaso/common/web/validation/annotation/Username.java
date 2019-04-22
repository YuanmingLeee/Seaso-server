package com.seaso.seaso.common.web.validation.annotation;

import com.seaso.seaso.common.web.validation.validatior.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UsernameValidator.class})
public @interface Username {
    String message() default "Username cannot contain invalid characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
