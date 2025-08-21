package com.example.taskapi.mapper;

import org.springframework.stereotype.Component;

import com.example.taskapi.dto.TaskDTO;
import com.example.taskapi.entity.Task;

@Component
public class TaskMapper {
    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted()
        );
    }
}
