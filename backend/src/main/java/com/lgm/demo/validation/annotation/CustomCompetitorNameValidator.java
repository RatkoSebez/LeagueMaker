package com.lgm.demo.validation.annotation;

import com.lgm.demo.validation.logic.CompetitorNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy=CompetitorNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCompetitorNameValidator{
    String message() default "Custom validator: invalid competitor name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
