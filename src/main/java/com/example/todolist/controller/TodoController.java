package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Task> findAll() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task findOne(@PathVariable Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @PostMapping
    public Task save(@Valid @NotNull @RequestBody Task task) {
        return todoRepository.save(task);
    }

    @PutMapping
    public Task update(@Valid @NotNull @RequestBody Task task) {
        if(todoRepository.findById(task.getId()).isPresent()) {
            return todoRepository.save(task);
        } else {
            throw new NoSuchElementException();
        }
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
