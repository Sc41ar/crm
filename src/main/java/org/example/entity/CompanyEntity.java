package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность компании
 */
@Entity
@Table(name = "company", schema = "public")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
    /**
     * Идентификатор компании
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Название компании
     */
    @Column(unique = true, nullable = false, length = 100)
    private String name;
    /**
     * Номер телефона
     */
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;
    /**
     * Электронная почта
     */
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    /**
     * Адрес
     */
    @Column(unique = true, nullable = false, length = 200)
    private String address;
    /**
     * Описание
     */
    @Column(length = 200)
    private String description;
}
