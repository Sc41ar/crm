package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO продукта
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    /**
     * Идентификатор продукта
     */
    @Null(message = "Id must be empty", groups = Marker.OnCreate.class)
    @NotNull(message = "Id cannot be empty", groups = Marker.OnUpdate.class)
    private Long id;
    /**
     * Название продукта
     */
    @NotBlank(message = "Name cannot be empty", groups = Marker.OnCreate.class)
    private String name;
    /**
     * Единица измерения
     */
    @NotBlank(message = "Unit cannot be empty", groups = Marker.OnCreate.class)
    private String unit;
    /**
     * Цена за единицу измерения
     */
    @NotNull(message = "Price cannot be null", groups = Marker.OnCreate.class)
    @Positive(message = "Price cannot be negative or equal to 0")
    private BigDecimal unitPrice;
}
