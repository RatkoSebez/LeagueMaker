package com.lgm.demo.validation.annotation;

import com.lgm.demo.validation.logic.MatchScoreValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy=MatchScoreValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomMatchScoreValidator{
    String message() default "Custom validator: invalid match score";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
