package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.repository.UserRepository;

public interface UserService {

    public void addTask(Long userId, Task task, UserRepository userRepository, TodoRepository todoRepository);
}
