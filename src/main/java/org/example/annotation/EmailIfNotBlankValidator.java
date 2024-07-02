package org.example.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Валидатор для EmailIfNotBlank
 */
public class EmailIfNotBlankValidator implements ConstraintValidator<EmailIfNotBlank, String> {
    @Override
    public void initialize(EmailIfNotBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        if (value.matches("^[\\w-_\\.]+@([\\w]+\\.)+[\\w-]+$")) {
            return true;
        } else {
            return false;
        }
    }
}
