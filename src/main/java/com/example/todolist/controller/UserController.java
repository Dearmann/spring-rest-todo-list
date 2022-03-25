package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/{userId}/task")
    public void addTask(@PathVariable Long userId, @RequestBody Task task) {
        userService.addTask(userId, task);
    }

    @PostMapping("/task/{taskId}")
    public void toggleTaskComplete(@PathVariable Long taskId) {
        userService.toggleTaskCompletion(taskId);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User newUser, @PathVariable Long id) {
        return userService.update(newUser, id);
    }

    @DeleteMapping("{userId}/task/{taskId}")
    public void deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
        userService.deleteTask(userId, taskId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
