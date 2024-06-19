package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO пользователя
 */
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * ФИО пользователя
     */
    @NotBlank
    private String fio;
    /**
     * Электронная почта
     */
    @NotBlank
    private String email;
    /**
     * Логин пользователя
     */
    @NotBlank
    private String username;
    /**
     * Пароль
     */
    @NotBlank
    private String password;
    /**
     * Подтверждение пароля
     */
    @NotBlank
    private String matchingPassword;
    /**
     * Роль пользователя
     */
    @NotBlank
    private String role;

}
