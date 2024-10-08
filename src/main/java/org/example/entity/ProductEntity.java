package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Сущность продукта
 */
@Entity
@Table(name = "product", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductEntity {
    /**
     * Идентификатор продукта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Название продукта
     */
    @Column(unique = true, nullable = false, length = 40)
    private String name;
    /**
     * Единица измерения
     */
    @Column(nullable = false, length = 8)
    private String unit;
    /**
     * Цена продукта за единицу измерения
     */
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal unitPrice;
}
