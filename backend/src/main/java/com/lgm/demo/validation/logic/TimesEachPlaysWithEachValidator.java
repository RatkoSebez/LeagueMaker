package com.lgm.demo.validation.logic;

import com.lgm.demo.validation.annotation.CustomTimesEachPlaysWithEachValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimesEachPlaysWithEachValidator implements ConstraintValidator<CustomTimesEachPlaysWithEachValidator, Integer> {
    @Override
    public void initialize(CustomTimesEachPlaysWithEachValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext constraintValidatorContext) {
        return (number != null) &&
                (number >= 1) && (number <= 6);
    }
}
