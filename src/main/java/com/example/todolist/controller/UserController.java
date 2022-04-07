package com.example.todolist.controller;

import com.example.todolist.dto.UserMapper;
import com.example.todolist.dto.UserDto;
import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userConverter) {
        this.userService = userService;
        this.userMapper = userConverter;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll().stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserDto userResponse = userMapper.entityToDto(user);
        return userResponse;
    }

    @PostMapping
    public UserDto addUser(@RequestBody @Validated UserDto userDto) {
        User userRequest = userMapper.dtoToEntity(userDto);
        User user = userService.addUser(userRequest);
        UserDto userResponse = userMapper.entityToDto(user);
        return userResponse;
    }

    @PutMapping("/{id}")
    public UserDto update(@RequestBody @Validated UserDto userDto, @PathVariable Long id) {
        User userRequest = userMapper.dtoToEntity(userDto);
        User user = userService.update(userRequest, id);
        UserDto userResponse = userMapper.entityToDto(user);
        return userResponse;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
