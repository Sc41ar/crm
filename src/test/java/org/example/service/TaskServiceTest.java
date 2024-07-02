package org.example.service;

import org.example.dto.TaskDto;
import org.example.enumeration.unit.TaskStatus;
import org.example.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void addTask_shouldCallRepository() {
        // Arrange
        TaskDto testTask = TaskDto.builder()
                .name("test")
                .description("description")
                .username("4")
                .userId(2L)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .deadline(LocalDateTime.now().plusMinutes(1))
                .status(TaskStatus.TODO)
                .build();


        // Act and Assert
        try {
            taskService.add(testTask);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void findAll_shouldReturnListOfDto() {
        List<TaskDto> actualList = taskService.findAll();

        // Arrange
        Assertions.assertNotNull(actualList);
    }

    @Test
    public void findAllByAuthorName_shouldReturnListOfDto() {
        List<TaskDto> actualList = taskService.findByUsername("test");

        // Arrange
        Assertions.assertNotNull(actualList);
    }

}
