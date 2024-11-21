package com.example.user.validation;

import com.example.user.model.dto.UserRegDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRegDto user = (UserRegDto) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
