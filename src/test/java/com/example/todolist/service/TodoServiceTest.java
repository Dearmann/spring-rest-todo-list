package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;
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
class TodoServiceTest {

    @InjectMocks
    private TodoServiceImpl todoService;
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private UserRepository userRepository;

    private User user = new User(4L,"TestUsername","TestPassword");;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Task task1 = new Task(1L,"TestTitle1",false, user);
        Task task2 = new Task(2L,"TestTitle2",false, user);
        Task task3 = new Task(3L,"TestTitle3",false, user);
        var list = List.of(task1,task2,task3);
        when(todoRepository.findAll()).thenReturn(list);

        List<Task> taskList = todoService.findAll();

        assertEquals(3, taskList.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void findOne() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(new Task(1L,"TestTitle",true,user)));

        Task task = todoService.findOne(1L);

        assertEquals(1L, (long) task.getId());
        assertEquals("TestTitle", task.getTitle());
        assertTrue(task.isDone());
    }

    @Test
    void findOneExceptionTest() {
        assertThrows(NoSuchElementException.class, () -> todoService.findOne(1L));
    }

    @Test
    void save() {
        Task task1 = new Task(1L,"TestTitle 1",false, user);
        Task task2 = new Task(2L,"TestTitle 2",false, user);
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));

        todoService.save(task1, 3L);
        todoService.save(task2, 3L);

        verify(todoRepository, times(1)).save(task1);
        verify(todoRepository, times(1)).save(task2);
        assertEquals(2,user.getTaskSet().size());
        assertTrue(user.getTaskSet().contains(task1));
        assertTrue(user.getTaskSet().contains(task2));
    }

    @Test
    void saveSameTask() {
        Task task1 = new Task(1L,"Same Title",false, user);
        Task task2 = new Task(2L,"Same Title",false, user);
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));

        todoService.save(task1, 3L);
        todoService.save(task2, 3L);

        verify(todoRepository, times(1)).save(task1);
        assertEquals(1,user.getTaskSet().size());
        assertTrue(user.getTaskSet().contains(task1));
        assertFalse(user.getTaskSet().contains(task2));
    }

    @Test
    void saveExceptionTest() {
        Task task = new Task(1L,"TestTitle",false, user);

        assertThrows(NoSuchElementException.class, () -> todoService.save(task, 1L));
    }

    @Test
    void toggleTaskCompletion() {
        Task task = new Task(1L,"TestTitle",false, user);
        when(todoRepository.findById(task.getId())).thenReturn(Optional.of(task));

        todoService.toggleTaskCompletion(task.getId());

        assertTrue(task.isDone());
    }

    @Test
    void toggleTaskCompletionExceptionTest() {
        assertThrows(NoSuchElementException.class, () -> todoService.toggleTaskCompletion(1L));
    }

    @Test
    void update() {
        Task task = new Task(1L,"TestTitle",false, user);

        assertThrows(NoSuchElementException.class, () -> todoService.update(task,1L));
    }

    @Test
    void updateExceptionTest() {
        Task task = new Task(1L,"TestTitle",false, user);

        assertThrows(NoSuchElementException.class, () -> todoService.update(task, 1L));
    }

    @Test
    void delete() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(new Task(1L,"TestTitle",false, user)));

        todoService.delete(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteExceptionTest() {
        assertThrows(NoSuchElementException.class, () -> todoService.delete(1L));
    }
}