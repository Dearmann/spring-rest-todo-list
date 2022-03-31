package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
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
    public void toggleTaskCompletion(Long taskId) {
        Task task = todoRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        task.setDone(!task.isDone());
        todoRepository.save(task);
    }

    @Override
    public Task update(Task newTask, Long id) {
        return todoRepository.findById(id)
                .map(task -> {
                    task.setTitle(newTask.getTitle());
                    task.setDone(newTask.isDone());
                    return todoRepository.save(task);
                })
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void delete(Long id) {
        Task task = todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        task.getUser().getTaskSet().remove(task);
        todoRepository.deleteById(id);
    }
}
