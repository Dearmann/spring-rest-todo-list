package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private final User user = new User(1L, "TestUsername", "TestPassword");
    private final Task task = new Task(2L,"TestTitle",false, user);

    @Test
    void read() throws Exception {
        userRepository.save(user);
        taskRepository.save(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TestTitle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
    }

    @Test
    void save() throws Exception {
        userRepository.save(user);
        taskRepository.save(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/user/1")
                .content(new ObjectMapper().writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        userRepository.save(user);
        taskRepository.save(task);
        Task newTask = new Task();
        newTask.setId(2L);
        newTask.setTitle("This is updated task");

        mockMvc.perform(MockMvcRequestBuilders.put("/todo/2")
                .content(new ObjectMapper().writeValueAsString(newTask))
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("This is updated task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
    }

    @Test
    void delete() throws Exception {
        userRepository.save(user);
        taskRepository.save(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}