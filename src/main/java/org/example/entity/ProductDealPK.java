package org.example.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Класс составного ключа для списков продуктов сделок
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class ProductDealPK implements Serializable {
    /**
     * Отношение многие-к-одному со сделкой
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private DealEntity deal;
    /**
     * Отношение многие-к-одному с продуктом сделки
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity product;
}
