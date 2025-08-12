package com.example.taskapi.mappers;

import org.springframework.stereotype.Component;

import com.example.taskapi.dtos.TaskDTO;
import com.example.taskapi.entities.Task;

@Component
public class TaskMapper {
    public static TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted()
        );
    }
}
