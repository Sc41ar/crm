package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientDto {
    /**
     * Идентификатор клиента
     */
    @Null(message = "Id must be empty", groups = Marker.OnCreate.class)
    @NotNull(message = "Id cannot be empty", groups = Marker.OnUpdate.class)
    private Long id;
    /**
     * Фамилия клиента
     */
    @NotBlank(message = "Last name cannot be empty", groups = Marker.OnCreate.class)
    private String lastName;
    /**
     * Имя клиента
     */
    @NotBlank(message = "Name cannot be empty", groups = Marker.OnCreate.class)
    private String name;
    /**
     * Отчество
     */
    private String middleName;
    /**
     * Номер телефона
     */
    @NotBlank(message = "Phone number cannot be empty", groups = Marker.OnCreate.class)
    @Pattern(message = "Phone number is not valid", regexp = "^\\+7\\d{10}$", groups = Marker.OnCreate.class)
    private String phoneNumber;
    /**
     * Электронная почта
     */
    @NotBlank(message = "Email cannot be empty", groups = Marker.OnCreate.class)
    @Email(message = "Email is not valid", regexp = "^[\\w-_\\.]+@([\\w]+\\.)+[\\w-]+$", groups = Marker.OnCreate.class)
    private String email;
    /**
     * Адрес
     */
    @NotBlank(message = "Address cannot be empty", groups = Marker.OnCreate.class)
    private String address;
    /**
     * Описание
     */
    private String description;
    /**
     * Идентификатор компании, которую представляет клиент
     */
    private Long idCompany;
    /**
     * Название компании
     */
    private String companyName;
}
