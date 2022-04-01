package com.example.todolist.controller;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.dto.TaskMapper;
import com.example.todolist.dto.UserDto;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.service.TaskService;
import com.example.todolist.service.TaskServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/todo")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> findAll() {
        return taskService.findAll().stream()
                .map(taskMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskDto findOne(@PathVariable Long id) {
        Task task = taskService.findOne(id);
        TaskDto taskResponse = taskMapper.entityToDto(task);
        return taskResponse;
    }

    @PostMapping("/user/{userId}")
    public TaskDto save(@RequestBody @Validated TaskDto taskDto, @PathVariable Long userId) {
        Task taskRequest = taskMapper.dtoToEntity(taskDto);
        Task task = taskService.save(taskRequest, userId);
        TaskDto taskResponse = taskMapper.entityToDto(task);
        return taskResponse;
    }

    @PostMapping("/{taskId}")
    public void toggleTaskCompletion(@PathVariable Long taskId) {
        taskService.toggleTaskCompletion(taskId);
    }

    @PutMapping("/{id}")
    public TaskDto update(@RequestBody @Validated TaskDto taskDto, @PathVariable Long id) {
        Task taskRequest = taskMapper.dtoToEntity(taskDto);
        Task task = taskService.update(taskRequest, id);
        TaskDto taskResponse = taskMapper.entityToDto(task);
        return taskResponse;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
