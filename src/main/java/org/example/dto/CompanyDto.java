package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.annotation.EmailIfNotBlank;
import org.example.annotation.PhoneNumberIfNotBlank;

/**
 * DTO компании
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompanyDto {
    /**
     * Идентификатор компании
     */
    @Null(message = "Id must be empty", groups = Marker.OnCreate.class)
    @NotNull(message = "Id cannot be empty", groups = Marker.OnUpdate.class)
    private Long id;
    /**
     * Название компании
     */
    @NotBlank(message = "Name cannot be empty", groups = Marker.OnCreate.class)
    private String name;
    /**
     * Номер телефона
     */
    @Pattern(message = "Phone number is not valid", regexp = "^\\+7\\d{10}$", groups = Marker.OnCreate.class)
    @PhoneNumberIfNotBlank(groups = Marker.OnUpdate.class)
    private String phoneNumber;
    /**
     * Электронная почта
     */
    @Email(message = "Email is not valid", regexp = "^[\\w-_\\.]+@([\\w]+\\.)+[\\w-]+$", groups = Marker.OnCreate.class)
    @EmailIfNotBlank(groups = Marker.OnUpdate.class)
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

}
