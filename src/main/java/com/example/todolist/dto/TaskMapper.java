package com.example.todolist.dto;

import com.example.todolist.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDto entityToDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.isDone());
    }

    public Task dtoToEntity(TaskDto taskDto) {
        return new Task(taskDto.getId(), taskDto.getTitle(), taskDto.isDone());
    }
}
