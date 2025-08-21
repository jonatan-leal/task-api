package com.example.taskapi.service;

import java.util.List;
import java.util.UUID;

import com.example.taskapi.dto.TaskDTO;
import com.example.taskapi.entity.User;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO, User user);

    List<TaskDTO> getAllUserTasks(User user);

    TaskDTO getUserTaskById(User user, UUID id);

    TaskDTO updateUserTask(User user, UUID id, TaskDTO taskDTO);

    void deleteUserTask(User user, UUID id);
}
