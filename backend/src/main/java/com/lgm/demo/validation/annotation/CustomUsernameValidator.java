package com.lgm.demo.validation.annotation;

import com.lgm.demo.validation.logic.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UsernameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUsernameValidator {
    String message() default "Custom validator: invalid username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
