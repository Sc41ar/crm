package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * DTO компании
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompanyDto {
    /**
     * Название компании
     */
    @NotBlank
    private String name;
    /**
     * Номер телефона
     */
    @NotBlank
    @Pattern(message = "Phone number is not valid", regexp = "^\\+7\\d{10}$")
    private String phoneNumber;
    /**
     * Электронная почта
     */
    @Email(message = "Email is not valid", regexp = "^[\\w-_\\.]+@([\\w]+\\.)+[\\w-]+$")
    @NotBlank
    private String email;
    /**
     * Адрес
     */
    @NotBlank
    private String address;
    /**
     * Описание
     */
    private String description;
}
