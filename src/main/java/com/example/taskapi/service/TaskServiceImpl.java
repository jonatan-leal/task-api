package com.example.taskapi.service;

import java.util.List;
import java.util.UUID;

import com.example.taskapi.entity.User;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.exception.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

import com.example.taskapi.dto.TaskDTO;
import com.example.taskapi.entity.Task;
import com.example.taskapi.mapper.TaskMapper;
import com.example.taskapi.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    private static final String TASK_NOT_FOUND = "Task not found";
    private static final String UNAUTHORIZED_ACCESS = "Unauthorized: Task does not belong to user";

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO, User user) {
        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setCompleted(taskDTO.completed());
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    @Override
    public List<TaskDTO> getAllUserTasks(User user) {
        return taskRepository.findAllByUser(user).stream()
                .map(taskMapper::toDTO).toList();
    }

    @Override
    public TaskDTO getUserTaskById(User user, UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND));
        validateOwner(user, task);
        return taskMapper.toDTO(task);
    }

    @Override
    public TaskDTO updateUserTask(User user, UUID id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND));
        validateOwner(user, task);
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setCompleted(taskDTO.completed());
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    public void deleteUserTask(User user, UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND));
        validateOwner(user, task);
        taskRepository.deleteById(id);
    }

    private void validateOwner(User user, Task task) {
        if (!task.getUser().equals(user)) {
            throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS);
        }
    }
}
