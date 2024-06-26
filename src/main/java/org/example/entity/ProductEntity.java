package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Сущность продукта
 */
@Entity
@Table(name = "product", schema = "public")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(nullable = false, length = 5)
    private String unit;
    /**
     * Цена продукта за единицу измерения
     */
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal unitPrice;
}
