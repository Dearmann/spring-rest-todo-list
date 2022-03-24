package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TodoService implements TodoServiceInterface {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
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
        if(todoRepository.findById(task.getId()).isPresent()) {
            return todoRepository.save(task);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
