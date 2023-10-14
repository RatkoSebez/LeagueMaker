package com.lgm.demo.validation.logic;

import com.lgm.demo.validation.annotation.CustomNameValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<CustomNameValidator, String> {
    @Override
    public void initialize(CustomNameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if(name == null) return true;
        return (name.length() <= 30) &&
                name.matches("^[a-zA-Z]*$");
    }
}
