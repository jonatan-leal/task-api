package com.example.taskapi.services;

import com.example.taskapi.dtos.TaskDTO;
import com.example.taskapi.entities.Task;
import com.example.taskapi.entities.User;
import com.example.taskapi.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository mockTaskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTask_Success() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        TaskDTO taskDTO = new TaskDTO(null, "Sample Task", "This is a sample description", false);

        Task savedTask = new Task();
        savedTask.setId(UUID.randomUUID());
        savedTask.setTitle(taskDTO.title());
        savedTask.setDescription(taskDTO.description());
        savedTask.setCompleted(taskDTO.completed());
        savedTask.setUser(user);

        when(mockTaskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Act
        TaskDTO result = taskService.createTask(taskDTO, user);

        // Assert
        assertNotNull(result.id());
        assertEquals(taskDTO.title(), result.title());
        assertEquals(taskDTO.description(), result.description());
        assertEquals(taskDTO.completed(), result.completed());
        verify(mockTaskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllUserTasks_Success() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        Task task1 = new Task();
        task1.setId(UUID.randomUUID());
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setCompleted(false);
        task1.setUser(user);

        Task task2 = new Task();
        task2.setId(UUID.randomUUID());
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setCompleted(true);
        task2.setUser(user);

        when(mockTaskRepository.findAllByUser(user)).thenReturn(List.of(task1, task2));

        // Act
        List<TaskDTO> result = taskService.getAllUserTasks(user);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).title());
        assertEquals("Task 2", result.get(1).title());
        verify(mockTaskRepository, times(1)).findAllByUser(user);
    }

    @Test
    void testGetAllUserTasks_EmptyList() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        when(mockTaskRepository.findAllByUser(user)).thenReturn(List.of());

        // Act
        List<TaskDTO> result = taskService.getAllUserTasks(user);

        // Assert
        assertEquals(0, result.size());
        verify(mockTaskRepository, times(1)).findAllByUser(user);
    }

    @Test
    void testGetUserTaskById_Success() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task description");
        task.setCompleted(false);
        task.setUser(user);

        when(mockTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        // Act
        TaskDTO result = taskService.getUserTaskById(user, task.getId());

        // Assert
        assertNotNull(result);
        assertEquals(task.getId(), result.id());
        assertEquals(task.getTitle(), result.title());
        assertEquals(task.getDescription(), result.description());
        verify(mockTaskRepository, times(1)).findById(task.getId());
    }

    @Test
    void testGetUserTaskById_NotFound() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        UUID taskId = UUID.randomUUID();

        when(mockTaskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> taskService.getUserTaskById(user, taskId));
        assertEquals("Task not found", exception.getMessage());
        verify(mockTaskRepository, times(1)).findById(taskId);
    }

    @Test
    void testGetUserTaskById_Unauthorized() {
        // Arrange
        User unauthorizedUser = new User();
        unauthorizedUser.setId(UUID.randomUUID());
        unauthorizedUser.setUsername("unauthorizedUser");
        unauthorizedUser.setPassword("password123");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("user");
        user.setPassword("password456");

        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task description");
        task.setCompleted(false);
        task.setUser(user);

        when(mockTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> taskService.getUserTaskById(unauthorizedUser, task.getId()));
        assertEquals("Unauthorized: Task does not belong to user", exception.getMessage());
        verify(mockTaskRepository, times(1)).findById(task.getId());
    }

    @Test
    void testUpdateUserTask_Success() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        Task existingTask = new Task();
        existingTask.setId(UUID.randomUUID());
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setCompleted(false);
        existingTask.setUser(user);

        TaskDTO updatedTaskDTO = new TaskDTO(null, "Updated Title", "Updated Description", true);

        Task updatedTask = new Task();
        updatedTask.setId(existingTask.getId());
        updatedTask.setTitle(updatedTaskDTO.title());
        updatedTask.setDescription(updatedTaskDTO.description());
        updatedTask.setCompleted(updatedTaskDTO.completed());
        updatedTask.setUser(user);

        when(mockTaskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));
        when(mockTaskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Act
        TaskDTO result = taskService.updateUserTask(user, existingTask.getId(), updatedTaskDTO);

        // Assert
        assertNotNull(result);
        assertEquals(existingTask.getId(), result.id());
        assertEquals(updatedTaskDTO.title(), result.title());
        assertEquals(updatedTaskDTO.description(), result.description());
        assertEquals(updatedTaskDTO.completed(), result.completed());
        verify(mockTaskRepository, times(1)).findById(existingTask.getId());
        verify(mockTaskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateUserTask_TaskNotFound() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        UUID nonexistentTaskId = UUID.randomUUID();
        TaskDTO updatedTaskDTO = new TaskDTO(null, "Updated Title", "Updated Description", true);

        when(mockTaskRepository.findById(nonexistentTaskId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                taskService.updateUserTask(user, nonexistentTaskId, updatedTaskDTO));
        assertEquals("Task not found", exception.getMessage());
        verify(mockTaskRepository, times(1)).findById(nonexistentTaskId);
        verify(mockTaskRepository, never()).save(any(Task.class));
    }

    @Test
    void testUpdateUserTask_Unauthorized() {
        // Arrange
        User unauthorizedUser = new User();
        unauthorizedUser.setId(UUID.randomUUID());
        unauthorizedUser.setUsername("unauthorizedUser");
        unauthorizedUser.setPassword("password123");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("user");
        user.setPassword("password456");

        Task existingTask = new Task();
        existingTask.setId(UUID.randomUUID());
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setCompleted(false);
        existingTask.setUser(user);

        TaskDTO updatedTaskDTO = new TaskDTO(null, "Updated Title", "Updated Description", true);

        when(mockTaskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                taskService.updateUserTask(unauthorizedUser, existingTask.getId(), updatedTaskDTO));
        assertEquals("Unauthorized: Task does not belong to user", exception.getMessage());
        verify(mockTaskRepository, times(1)).findById(existingTask.getId());
        verify(mockTaskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteUserTask_Success(){
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task description");
        task.setCompleted(false);
        task.setUser(user);

        when(mockTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        // Act
        taskService.deleteUserTask(user, task.getId());

        // Assert
        verify(mockTaskRepository, times(1)).findById(task.getId());
        verify(mockTaskRepository, times(1)).deleteById(task.getId());
    }

    @Test
    void testDeleteUserTask_TaskNotFound(){
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        user.setPassword("password123");

        UUID nonexistentTaskId = UUID.randomUUID();

        when(mockTaskRepository.findById(nonexistentTaskId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> taskService.deleteUserTask(user, nonexistentTaskId));
        assertEquals("Task not found", exception.getMessage());
        verify(mockTaskRepository, times(1)).findById(nonexistentTaskId);
        verify(mockTaskRepository, never()).deleteById(nonexistentTaskId);
    }

    @Test
    void testDeleteUserTask_Unauthorized(){
        // Arrange
        User unauthorizedUser = new User();
        unauthorizedUser.setId(UUID.randomUUID());
        unauthorizedUser.setUsername("unauthorizedUser");
        unauthorizedUser.setPassword("password123");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("user");
        user.setPassword("password456");

        Task existingTask = new Task();
        existingTask.setId(UUID.randomUUID());
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setCompleted(false);
        existingTask.setUser(user);

        when(mockTaskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> taskService.deleteUserTask(unauthorizedUser, existingTask.getId()));
        assertEquals("Unauthorized: Task does not belong to user", exception.getMessage());
        verify(mockTaskRepository, times(1)).findById(existingTask.getId());
        verify(mockTaskRepository, never()).deleteById(existingTask.getId());
    }
}