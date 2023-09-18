package com.lgm.demo.model.validation.logic;

import com.lgm.demo.model.validation.annotation.CustomCompetitionNameValidator;
import com.lgm.demo.model.validation.annotation.CustomNameValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompetitionNameValidator implements ConstraintValidator<CustomCompetitionNameValidator, String> {
    @Override
    public void initialize(CustomCompetitionNameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if(name == null) return false;
        return (name.length() >= 3) &&
                (name.length() <= 30) &&
                name.matches("^[a-zA-Z0-9 ]*$");
    }
}
