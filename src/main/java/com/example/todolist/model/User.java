package com.example.todolist.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
    private List<Task> taskSet = new ArrayList<>();

    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, List<Task> taskSet) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.taskSet = taskSet;
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

    public List<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(List<Task> taskSet) {
        this.taskSet = taskSet;
    }
}
