package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.dto.Marker;
import org.example.dto.TaskDto;
import org.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Validated({Marker.OnCreate.class})
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addTask(@Valid @RequestBody TaskDto taskDto, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            taskService.add(taskDto);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Validated({Marker.OnUpdate.class})
    @PatchMapping(path = "/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TaskDto patchStatus(@Valid @RequestBody TaskDto taskDto, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return taskService.updateStatus(taskDto);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return TaskDto.builder().name(e.getLocalizedMessage()).build();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TaskDto> getAllTasks(@RequestParam("username") String username, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return taskService.findByUsername(username);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }
}
