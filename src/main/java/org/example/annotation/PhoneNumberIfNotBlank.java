package org.example.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для валидации номера телефона при обновлении записи
 */
@Constraint(validatedBy = PhoneNumberIfNotBlankValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberIfNotBlank {
    String message() default "Phone number is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
