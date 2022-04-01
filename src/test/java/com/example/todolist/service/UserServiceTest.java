package com.example.todolist.service;

import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private TaskRepository todoRepository;
    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        User user1 = new User(1L,"TestUsername1","TestPassword1");
        User user2 = new User(2L,"TestUsername2","TestPassword2");
        User user3 = new User(3L,"TestUsername3","TestPassword3");

        var list = List.of(user1,user2,user3);

        when(userRepository.findAll()).thenReturn(list);

        List<User> userList = userService.findAll();

        assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "TestUsername", "TestPassword")));

        User user = userService.getUserById(1L);

        assertEquals(1L, (long) user.getId());
        assertEquals("TestUsername", user.getUsername());
        assertEquals("TestPassword", user.getPassword());
    }

    @Test
    void getUserByIdExceptionTest() {
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1L));
    }

    @Test
    void addUser() {
        User user = new User(1L,"TestUsername","TestPassword");

        userService.addUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update() {
        User user = new User(1L,"TestUsername","TestPassword");

        assertThrows(NoSuchElementException.class, () -> userService.update(user,1L));
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}