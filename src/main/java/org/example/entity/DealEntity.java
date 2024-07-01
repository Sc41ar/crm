package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Сущность сделки
 */
@Entity
@Table(name = "deal", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealEntity {
    /**
     * Идентификатор сделки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Название сделки
     */
    @Column(unique = true, nullable = false, length = 100)
    private String name;
    /**
     * Этап сделки
     */
    @Column(nullable = false, length = 30)
    private String stage;
    /**
     * Стоимость сделки
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal totalCost;
    /**
     * Тип сделки
     */
    @Column(nullable = false, length = 20)
    private String type;
    /**
     * Дата начала сделки
     */
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    /**
     * Дата окончания сделки
     */
    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;
    /**
     * Отношение многие-к-одному с клиентом
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ClientEntity client;
    /**
     * Отношение многие-к-одному с пользователем-сотрудником
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity user;
}
