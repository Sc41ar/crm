package org.example.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

/**
 * Класс составного ключа для списков продуктов сделок
 */
@Data
@Embeddable
public class ProductDealPK implements Serializable {
    /**
     * Отношение многие-к-одному со сделкой
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deal", nullable = false)
    private DealEntity deal;
    /**
     * Отношение многие-к-одному с продуктом сделки
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product", nullable = false)
    private ProductEntity product;
}
