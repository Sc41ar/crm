package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.example.enumeration.unit.TaskStatus;

import java.time.LocalDateTime;


/**
 * Класс TaskDto представляет собой объект для передачи данных о задаче.
 * Он используется для валидации входных данных и обеспечения их согласованности.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskDto {
    /**
     * Уникальный идентификатор задачи.
     * Должен быть пустым при создании новой задачи и не пустым при обновлении существующей.
     */
    @Null(message = "id должен быть пустым", groups = Marker.OnCreate.class)
    @NotNull(message = "id не должен быть пустым", groups = Marker.OnUpdate.class)
    private Long id;

    /**
     * Название задачи.
     * Не должно быть пустым при создании новой задачи.
     */
    @NotBlank(message = "название не должно быть пустым", groups = Marker.OnCreate.class)
    private String name;

    /**
     * Описание задачи.
     * Не должно быть пустым при создании новой задачи.
     */
    @NotBlank(message = "описание не должно быть пустым", groups = Marker.OnCreate.class)
    private String description;

    /**
     * Статус задачи.
     * Не должен быть пустым при создании или обновлении задачи.
     */
    @NotNull(message = "статус не должен быть пустым", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private TaskStatus status;

    /**
     * Дата создания задачи.
     * Не должна быть пустой при создании новой задачи.
     */
    @NotBlank(message = "дата создания не должна быть пустой", groups = Marker.OnCreate.class)
    private LocalDateTime createdAt;

    /**
     * Идентификатор пользователя, создавшего задачу.
     * Не должен быть пустым при создании новой задачи.
     */
    private Long userId;

    /**
     * Имя пользователя, создавшего задачу.
     * Не должно быть пустым при создании новой задачи.
     */
    @NotBlank(message = "имя пользователя не должно быть пустым", groups = Marker.OnCreate.class)
    private String username;


    /**
     * Дата, после которой, задача не имеет смысла.
     * Может быть пустой.
     */
    private LocalDateTime expiresAt;

    /**
     * Крайний срок выполнения задачи. Может быть не строгим.
     * Не должен быть пустым при создании новой задачи.
     */
    @NotBlank(message = "срок выполнения не должен быть пустым", groups = Marker.OnCreate.class)
    private LocalDateTime deadline;
}
