package com.lgm.demo.model.validation.annotation;

import com.lgm.demo.model.validation.logic.NameValidator;
import com.lgm.demo.model.validation.logic.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomNameValidator {
    String message() default "Custom validator: invalid name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
