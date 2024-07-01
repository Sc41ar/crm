package org.example.service;


import lombok.Setter;
import org.example.dto.TaskDto;
import org.example.entity.TaskEntity;
import org.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
public class TaskService implements ServiceInterface<TaskDto> {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void findByName(String taskName) {
    }

    @Override
    public void add(TaskDto taskDto) throws Exception {
        TaskEntity taskEntity = TaskEntity.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .createdAt(taskDto.getCreatedAt())
                .updatedAt(taskDto.getUpdatedAt())
                .deletedAt(taskDto.getDeletedAt())
                .build();
        taskRepository.save(taskEntity);
    }

    @Override
    public List<TaskDto> findAll() {
        List<TaskEntity> entityList = taskRepository.findAll();
        List<TaskDto> taskDtos = new ArrayList<>();
        for (TaskEntity entity : entityList) {
            TaskDto taskDto = TaskDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .status(entity.getStatus())
                    .createdAt(entity.getCreatedAt())
                    .expiresAt(entity.getExpiresAt())
                    .deadline(entity.getDeadline())
                    .build();
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }

    @Override
    public void update(TaskDto taskDto) throws Exception {
        Optional<TaskEntity> taskEntityOptional = taskRepository.findById(taskDto.getId());
        if (taskEntityOptional.isEmpty()) {
            throw new Exception("No record found for update");
        }
        TaskEntity taskEntity = taskEntityOptional.get();
        if (taskDto.getName() != null && !taskDto.getName().trim().isEmpty()) {
            taskEntity.setName(taskDto.getName());
        }
        if (taskDto.getDescription() != null && !taskDto.getDescription().trim().isEmpty()) {
            taskEntity.setDescription(taskDto.getDescription());
        }
        if (taskDto.getStatus() != null) {
            taskEntity.setStatus(taskDto.getStatus());
        }
        if (taskDto.getCreatedAt() != null) {
            taskEntity.setCreatedAt(taskDto.getCreatedAt());
        }
        if (taskDto.getExpiresAt() != null) {
            taskEntity.setExpiresAt(taskDto.getExpiresAt());
        }
        if (taskDto.getDeadline() != null) {
            taskEntity.setDeadline(taskDto.getDeadline());
        }
        taskRepository.save(taskEntity);
    }
}
