package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import com.example.todolist.service.TaskServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task findOne(@PathVariable Long id) {
        return taskService.findOne(id);
    }

    @PostMapping("/user/{userId}")
    public Task save(@RequestBody Task task, @PathVariable Long userId) {
        return taskService.save(task, userId);
    }

    @PostMapping("/{taskId}")
    public void toggleTaskCompletion(@PathVariable Long taskId) {
        taskService.toggleTaskCompletion(taskId);
    }

    @PutMapping("/{id}")
    public Task update(@RequestBody Task newTask, @PathVariable Long id) {
       return taskService.update(newTask, id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
