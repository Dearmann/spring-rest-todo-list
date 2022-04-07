package com.example.todolist.dto;

import com.example.todolist.model.Task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UserDto {
    private Long id;
    @NotBlank
    private String username;
    @NotNull
    private String password;
    private List<Task> taskList;

    public UserDto(Long id, String username, String password, List<Task> taskList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.taskList = taskList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
