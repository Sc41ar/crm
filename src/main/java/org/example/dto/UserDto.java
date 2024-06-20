package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO пользователя
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
     * Роль пользователя
     */
    @NotBlank
    private String role;

}
