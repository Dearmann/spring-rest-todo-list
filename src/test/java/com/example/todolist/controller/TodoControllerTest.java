package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private TodoController todoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void read() throws Exception {
        Task testTask1 = new Task();
        testTask1.setTitle("This is test task 1");
        todoController.save(testTask1);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("This is test task 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
    }

    @Test
    void save() throws Exception {
        Task testTask1 = new Task();
        testTask1.setTitle("This is test task 1");
        todoController.save(testTask1);

        mockMvc.perform(MockMvcRequestBuilders.post("/todo")
                .content(new ObjectMapper().writeValueAsString(testTask1))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("This is test task 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
    }

    @Test
    void update() throws Exception {
        Task testTask1 = new Task();
        testTask1.setId(1);
        testTask1.setTitle("This is updated task");
        todoController.save(testTask1);

        mockMvc.perform(MockMvcRequestBuilders.put("/todo")
                .content(new ObjectMapper().writeValueAsString(testTask1))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("This is updated task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
    }

    @Test
    void delete() throws Exception {
        Task testTask1 = new Task();
        testTask1.setTitle("This is test task 1");
        todoController.save(testTask1);

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}