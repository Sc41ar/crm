package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DealDto {
    /**
     * Идентификатор сделки
     */
    @Null(message = "Id must be empty", groups = Marker.OnCreate.class)
    @NotNull(message = "Id cannot be empty", groups = Marker.OnUpdate.class)
    private Long id;
    /**
     * Название сделки
     */
    @NotBlank(message = "Name cannot be null", groups = Marker.OnCreate.class)
    private String name;
    /**
     * Этап сделки
     */
    @NotBlank(message = "Stage cannot be null", groups = Marker.OnCreate.class)
    private String stage;
    /**
     * Стоимость сделки
     */
    @Null(message = "Total cost is calculated automatically", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private BigDecimal totalCost;
    /**
     * Тип сделки
     */
    @NotBlank(message = "Type cannot be null", groups = Marker.OnCreate.class)
    private String type;
    /**
     * Дата начала сделки
     */
    @NotNull(message = "Start date cannot be null", groups = Marker.OnCreate.class)
    private Date startDate;
    /**
     * Дата окончания сделки
     */
    private Date endDate;
    /**
     * Идентификатор клиента
     */
    @NotNull(message = "Client Id cannot be null", groups = Marker.OnCreate.class)
    private Long clientId;
    /**
     * ФИО клиента, участвующего в сделке
     */
    private String clientFio;
    /**
     * Имя пользователя-сотрудника, ответственного за сделку
     */
    @NotBlank(message = "Username cannot be null", groups = Marker.OnCreate.class)
    private String userUsername;
}
