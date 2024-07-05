package org.example.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для валидации электронной почты при обновлении записи
 */
@Constraint(validatedBy = EmailIfNotBlankValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailIfNotBlank {
    String message() default "Email is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
