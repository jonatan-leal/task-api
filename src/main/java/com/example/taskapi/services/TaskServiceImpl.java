package com.example.taskapi.services;

import java.util.List;
import java.util.UUID;

import com.example.taskapi.entities.User;
import org.springframework.stereotype.Service;

import com.example.taskapi.dtos.TaskDTO;
import com.example.taskapi.entities.Task;
import com.example.taskapi.mappers.TaskMapper;
import com.example.taskapi.repositories.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO, User user) {
        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setCompleted(taskDTO.completed());
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.convertToDTO(savedTask);
    }

    @Override
    public List<TaskDTO> getAllUserTasks(User user) {
        return taskRepository.findAllByUser(user).stream()
                .map(TaskMapper::convertToDTO).toList();
    }

    @Override
    public TaskDTO getUserTaskById(User user, UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        validateOwner(user, task);
        return TaskMapper.convertToDTO(task);
    }

    @Override
    public TaskDTO updateUserTask(User user, UUID id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        validateOwner(user, task);
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setCompleted(taskDTO.completed());
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.convertToDTO(updatedTask);
    }

    @Override
    public void deleteUserTask(User user, UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        validateOwner(user, task);
        taskRepository.deleteById(id);
    }

    private void validateOwner(User user, Task task) {
        if (!task.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized: Task does not belong to user");
        }
    }
}
