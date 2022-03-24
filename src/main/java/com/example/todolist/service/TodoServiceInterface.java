package com.example.todolist.service;

import com.example.todolist.model.Task;

import java.util.List;

public interface TodoServiceInterface {

    public List<Task> findAll();
    public Task findOne(Long id);
    public Task save(Task task);
    public Task update(Task task);
    public void delete(Long id);
}