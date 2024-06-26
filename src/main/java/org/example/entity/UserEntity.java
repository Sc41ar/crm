package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Сущность зарегистрированного пользователя
 */
@Entity
@Table(name = "usr", schema = "public")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Логин пользователя
     */
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    /**
     * Пароль
     */
    @Column(nullable = false)
    private String password;
    /**
     * Роль пользователя
     */
    @Column(nullable = false, length = 10)
    private String role;
    /**
     * ФИО пользователя
     */
    @Column(nullable = false, length = 35)
    private String fio;
    /**
     * Электронная почта
     */
    @Column(unique = true, nullable = false, length = 50)
    private String email;
}