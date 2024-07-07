package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO продуктов сделки
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ProductDealDto {
    /**
     * Идентификатор сделки
     */
    @NotNull(message = "Deal ID cannot be null", groups = Marker.OnUpdate.class)
    private Long dealId;
    /**
     * Название сделки
     */
    @NotNull(message = "Deal name cannot be null", groups = Marker.OnCreate.class)
    private String dealName;
    /**
     * Идентификатор продукта
     */
    @NotNull(message = "Product ID cannot be null", groups = Marker.OnUpdate.class)
    private Long productId;
    /**
     * Название продукта
     */
    @NotNull(message = "Product name cannot be null", groups = Marker.OnCreate.class)
    private String productName;
    /**
     * Количество продуктов
     */
    @NotNull(message = "Quantity cannot be null", groups = Marker.OnCreate.class)
    @Positive(message = "Quantity cannot be negative or equal to 0", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private BigDecimal quantity;
    /**
     * Стоимость товаров
     */
    @Null(message = "Cost is calculated automatically", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private BigDecimal cost;
    /**
     * Единица измерения
     */
    @NotBlank(message = "Unit cannot be empty", groups = Marker.OnCreate.class)
    private String unit;
}
