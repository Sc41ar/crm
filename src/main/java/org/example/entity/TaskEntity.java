package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
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
@Builder
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
     * Автор задачи
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UserEntity author;

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
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    /**
     * Дата и время удаления задачи
     */
    @Column(name = "deadline")
    private LocalDateTime deadline;
}