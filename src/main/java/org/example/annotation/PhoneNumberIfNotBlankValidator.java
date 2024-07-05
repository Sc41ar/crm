package org.example.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Валидатор для PhoneNumberIfNotBlank
 */
public class PhoneNumberIfNotBlankValidator implements ConstraintValidator<PhoneNumberIfNotBlank, String> {
    @Override
    public void initialize(PhoneNumberIfNotBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        if (value.matches("^\\+7\\d{10}$")) {
            return true;
        } else {
            return false;
        }
    }
}
