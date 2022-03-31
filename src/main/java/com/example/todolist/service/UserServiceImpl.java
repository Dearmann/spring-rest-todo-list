package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public UserServiceImpl(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addTask(Long userId, Task task) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        boolean taskIsUnique = true;
        for (Task taskCompare : user.getTaskSet()) {
            if (taskCompare.getTitle().equals(task.getTitle())) {
                taskIsUnique = false;
            }
        }
        if (taskIsUnique) {
            task.setUser(user);
            user.getTaskSet().add(task);
            todoRepository.save(task);
            userRepository.save(user);
        }
    }

    @Override
    public User update(User newUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setTaskSet(newUser.getTaskSet());
                    return userRepository.save(user);
                })
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
