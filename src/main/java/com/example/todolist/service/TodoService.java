package com.example.todolist.service;

import com.example.todolist.model.Task;

import java.util.List;

public interface TodoService {

    List<Task> findAll();
    Task findOne(Long id);
    Task save(Task task, Long userId);
    void toggleTaskCompletion(Long taskId);
    Task update(Task newTask, Long id);
    void delete(Long id);
}
