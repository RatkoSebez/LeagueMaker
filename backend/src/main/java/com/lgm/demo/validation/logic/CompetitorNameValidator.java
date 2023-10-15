package com.lgm.demo.validation.logic;

import com.lgm.demo.validation.annotation.CustomCompetitorNameValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompetitorNameValidator implements ConstraintValidator<CustomCompetitorNameValidator,String>{
    @Override
    public void initialize(CustomCompetitorNameValidator constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext){
        if(name == null) return false;
        return (name.length() > 2) &&
                (name.length() <= 30) &&
                name.matches("^[a-zA-Z0-9 ]*$");
    }
}
