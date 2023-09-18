package com.lgm.demo.model.validation.logic;

import com.lgm.demo.model.validation.annotation.CustomPasswordValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<CustomPasswordValidator, String> {
    @Override
    public void initialize(CustomPasswordValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return (password != null) &&
                (password.length() >= 8) &&
                (password.length() <= 50) &&
                password.matches("^[a-zA-Z0-9!@#&]*$");
    }
}
