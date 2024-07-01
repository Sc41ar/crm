package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enumeration.unit.TaskStatus;

import java.time.LocalDateTime;

/**
 * Сущность задачи
 */
@Entity
@Table(name = "tasks", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    /**
     * Идентификатор задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название задачи
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание задачи
     */
    @Column(name = "description")
    private String description;

    /**
     * Статус задачи
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    /**
     * Дата и время создания задачи
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления задачи
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Дата и время удаления задачи
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}