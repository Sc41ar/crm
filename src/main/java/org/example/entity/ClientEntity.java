package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность клиента
 */
@Entity
@Table(name = "client", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    /**
     * Идентификатор клиента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Фамилия
     */
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;
    /**
     * Имя клиента
     */
    @Column(nullable = false, length = 30)
    private String name;
    /**
     * Отчество
     */
    @Column(name = "middle_name", length = 30)
    private String middleName;
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
    /**
     * Отношение многие-к-одному с компанией
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private CompanyEntity company;
}
