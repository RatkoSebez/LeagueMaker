package com.lgm.demo.model.validation.logic;

import com.lgm.demo.model.validation.annotation.CustomNumberOfCompetitorsInLeagueValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberOfCompetitorsInLeague implements ConstraintValidator<CustomNumberOfCompetitorsInLeagueValidator, Integer> {
    @Override
    public void initialize(CustomNumberOfCompetitorsInLeagueValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext constraintValidatorContext) {
        return (number != null) &&
                (number >= 3) && (number <= 30);
    }
}
