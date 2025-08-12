package com.example.taskapi.services;

import java.util.List;
import java.util.UUID;

import com.example.taskapi.dtos.TaskDTO;
import com.example.taskapi.entities.User;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO, User user);

    List<TaskDTO> getAllUserTasks(User user);

    TaskDTO getUserTaskById(User user, UUID id);

    TaskDTO updateUserTask(User user, UUID id, TaskDTO taskDTO);

    void deleteUserTask(User user, UUID id);
}
