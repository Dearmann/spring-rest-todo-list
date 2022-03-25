package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    @Autowired
    private final TodoServiceImpl todoService;

    public TodoController(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Task> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public Task findOne(@PathVariable Long id) {
        return todoService.findOne(id);
    }

    @PostMapping
    public Task save(@Valid @NotNull @RequestBody Task task) {
        return todoService.save(task);
    }

    @PutMapping("/{id}")
    public Task update(@Valid @NotNull @RequestBody Task newTask, @PathVariable Long id) {
       return todoService.update(newTask, id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }
}
