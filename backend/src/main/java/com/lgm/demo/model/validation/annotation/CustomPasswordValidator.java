package com.lgm.demo.model.validation.annotation;

import com.lgm.demo.model.validation.logic.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPasswordValidator {
    String message() default "Custom validator: invalid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
