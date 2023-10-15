package com.lgm.demo.validation.logic;

import com.lgm.demo.validation.annotation.CustomNumberOfCompetitorsInTournamentValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberOfCompetitorsInTournamentValidator implements ConstraintValidator<CustomNumberOfCompetitorsInTournamentValidator,Integer>{
    @Override
    public void initialize(CustomNumberOfCompetitorsInTournamentValidator constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer numberOfCompetitors, ConstraintValidatorContext constraintValidatorContext){
        if(numberOfCompetitors == null) return false;
        return (numberOfCompetitors >= 2) && (numberOfCompetitors <= 64);
    }
}
