package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность зарегистрированного пользователя
 */
@Entity
@Table(name = "usr", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity{
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Логин пользователя
     */
    @Column
    private String username;
    /**
     * Пароль
     */
    @Column
    private String password;
    /**
     * Роль пользователя
     */
    @Column
    private String role;
    /**
     * ФИО пользователя
     */
    @Column
    private String fio;
    /**
     * Электронная почта
     */
    @Column
    private String email;
}