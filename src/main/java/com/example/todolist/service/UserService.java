package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User getUserById(Long userId);
    User addUser( User user);
    User update(User newUser, Long id);
    void deleteUser(Long userId);
}
