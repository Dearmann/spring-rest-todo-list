package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void addTask(Long userId, Task task, UserRepository userRepository, TodoRepository todoRepository) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        boolean taskIsUnique = true;
        for (Task taskCompare : user.getTaskSet()) {
            if (taskCompare.getTitle().equals(task.getTitle())) {
                taskIsUnique = false;
            }
        }
        if (taskIsUnique) {
            user.getTaskSet().add(task);
            todoRepository.save(task);
            userRepository.save(user);
        }
    }
}
