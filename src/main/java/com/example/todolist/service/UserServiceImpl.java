package com.example.todolist.service;

import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository todoRepository;

    public UserServiceImpl(UserRepository userRepository, TaskRepository todoRepository) {
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
    public User update(User newUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
