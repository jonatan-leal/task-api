package com.example.taskapi.controller;

import java.util.List;
import java.util.UUID;

import com.example.taskapi.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.taskapi.dto.TaskDTO;
import com.example.taskapi.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/tasks")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid task data provided")
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@AuthenticationPrincipal User user, @Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO, user);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTasks(@AuthenticationPrincipal User user) {
        List<TaskDTO> tasks = taskService.getAllUserTasks(user);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a task by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Task found and retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        TaskDTO task = taskService.getUserTaskById(user, id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Operation(summary = "Update task by ID", description = "Updates an existing task with the provided details")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "400", description = "Invalid task data provided")
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@AuthenticationPrincipal User user, @PathVariable UUID id, @Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateUserTask(user, id, taskDTO);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @Operation(summary = "Delete task by ID", description = "Deletes a task by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        taskService.deleteUserTask(user, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
