package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public UserService(UserRepository userRepository, TodoRepository todoRepository) {
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
            user.getTaskSet().add(task);
            todoRepository.save(task);
            userRepository.save(user);
        }
    }

    @Override
    public void toggleTaskComplete(Long taskId) {
        Task task = todoRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        task.setDone(!task.isDone());
        todoRepository.save(task);
    }

    @Override
    public User update(User user) {
        if(userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Task task = todoRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        user.getTaskSet().remove(task);
        todoRepository.delete(task);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        userRepository.delete(user);
    }
}
