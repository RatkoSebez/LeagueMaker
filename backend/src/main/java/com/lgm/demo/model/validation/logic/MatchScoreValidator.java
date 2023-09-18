package com.lgm.demo.model.validation.logic;

import com.lgm.demo.model.validation.annotation.CustomMatchScoreValidator;
import com.lgm.demo.model.validation.annotation.CustomNumberOfCompetitorsInTournamentValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchScoreValidator implements ConstraintValidator<CustomMatchScoreValidator, Integer> {
    @Override
    public void initialize(CustomMatchScoreValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer matchScore, ConstraintValidatorContext constraintValidatorContext) {
        if(matchScore == null) return false;
        return (matchScore >= 0);
    }
}
