package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Сущность для списков продуктов сделок
 */
@Entity
@Table(name = "product_deal", schema = "public")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDealEntity {
    /**
     * Составной первичный ключ
     */
    @EmbeddedId
    private ProductDealPK pk;
    /**
     * Количество товаров
     */
    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;
    /**
     * Стоимость товаров
     */
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal cost;
    /**
     * Единица измерения
     */
    @Column(nullable = false, length = 4)
    private String unit;
}
