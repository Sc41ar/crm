package org.example.service;

import org.example.dto.TaskDto;
import org.example.enumeration.TaskStatus;
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

    // @Mock
    // private TaskRepository taskRepository;

    // @InjectMocks
    // private TaskService taskService;


    // @Test
    // public void findAll_shouldReturnListOfDto() {
    //     List<TaskDto> actualList = taskService.findAll();

    //     // Arrange
    //     Assertions.assertNotNull(actualList);
    // }

    // @Test
    // public void findAllByAuthorName_shouldReturnListOfDto() {
    //     List<TaskDto> actualList = taskService.findByUsername("test");

    //     // Arrange
    //     Assertions.assertNotNull(actualList);
    // }

}
