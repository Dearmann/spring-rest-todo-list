package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;
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

    private User user = new User(4L,"TestUsername","TestPassword");;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
//        user
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
    void save() {
        Task task = new Task(1L,"TestTitle",false, user);

        todoService.save(task);

        verify(todoRepository, times(1)).save(task);
    }

    @Test
    void toggleTaskCompletion() {
        Task task = new Task(1L,"TestTitle",false, user);
        when(todoRepository.findById(task.getId())).thenReturn(Optional.of(task));

        todoService.toggleTaskCompletion(task.getId());

        assertTrue(task.isDone());
    }

    @Test
    void update() {
        Task task = new Task(1L,"TestTitle",false, user);

        assertThrows(NoSuchElementException.class, () -> todoService.update(task,1L));
    }

    @Test
    void delete() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(new Task(1L,"TestTitle",false, user)));

        todoService.delete(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }
}