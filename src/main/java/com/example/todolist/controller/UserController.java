package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private TodoRepository todoRepository;

    public UserController(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/{userId}/task")
    public void addTask(@PathVariable Long userId, @RequestBody Task task) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        user.getTaskSet().add(task);
        todoRepository.save(task);
        userRepository.save(user);
    }

    @PostMapping("/task/{taskId}")
    public void toggleTaskComplete(@PathVariable Long taskId) {
        Task task = todoRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException());
        task.setDone(!task.isDone());
        todoRepository.save(task);
    }

    // TODO - Deletes all tasks
    @PutMapping
    public User update(@RequestBody User user) {
        if(userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException();
        }
    }

    @DeleteMapping("{userId}/task/{taskId}")
    public void deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        Task task = todoRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException());
        user.getTaskSet().remove(task);
        todoRepository.delete(task);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        userRepository.delete(user);
    }
}
