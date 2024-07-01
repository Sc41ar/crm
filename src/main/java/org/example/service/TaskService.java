package org.example.service;


import lombok.Setter;
import org.example.dto.TaskDto;
import org.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(TaskDto taskDto) {

    }

    public void updateTask(String taskName) {

    }

    public void findByName(String taskName) {
    }
}
