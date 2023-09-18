package com.lgm.demo.model.validation.logic;

import com.lgm.demo.model.validation.annotation.CustomUsernameValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<CustomUsernameValidator, String> {
    @Override
    public void initialize(CustomUsernameValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return (username != null) &&
                (username.length() >= 3) &&
                (username.length() <= 20) &&
                username.matches("^[a-zA-Z0-9_]*$");
    }
}
