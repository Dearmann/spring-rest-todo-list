package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Task> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Task findOne(Long id) {
        return todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Task save(Task task) {
        return todoRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        todoRepository.findById(task.getId()).orElseThrow(NoSuchElementException::new);
        return todoRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
