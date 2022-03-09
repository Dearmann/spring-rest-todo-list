package com.example.todolist;

import com.example.todolist.controller.TodoController;
import com.example.todolist.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplication implements CommandLineRunner {

    @Autowired
    TodoController todoController;

    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Task testTask1 = new Task();
//        testTask1.setTitle("This is test task 1");
//        Task testTask2 = new Task();
//        testTask2.setTitle("This is test task 2");
//        testTask2.setDone(true);
//        todoController.save(testTask1);
//        todoController.save(testTask2);
    }
}
