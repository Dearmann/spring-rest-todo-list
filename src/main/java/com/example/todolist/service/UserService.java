package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User getUserById(Long userId);
    User addUser( User user);
    void addTask(Long userId, Task task);
    void toggleTaskCompletion(Long taskId);
    User update(User user);
    void deleteTask(Long userId, Long taskId);
    void deleteUser(Long userId);
}
