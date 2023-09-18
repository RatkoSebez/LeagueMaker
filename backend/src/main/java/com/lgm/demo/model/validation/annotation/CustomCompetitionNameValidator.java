package com.lgm.demo.model.validation.annotation;

import com.lgm.demo.model.validation.logic.CompetitionNameValidator;
import com.lgm.demo.model.validation.logic.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CompetitionNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCompetitionNameValidator {
    String message() default "Custom validator: invalid competition name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
