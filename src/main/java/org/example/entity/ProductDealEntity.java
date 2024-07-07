package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

/**
 * Сущность для списков продуктов сделок
 */
@Entity
@Table(name = "product_deal", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDealEntity {
    /**
     * Составной первичный ключ
     */
    @EmbeddedId
    private ProductDealPK id;
    /**
     * Количество продуктов
     */
    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;
    /**
     * Стоимость продуктов
     */
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal cost;
    /**
     * Единица измерения
     */
    @Column(nullable = false, length = 8)
    private String unit;
}
