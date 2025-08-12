package com.example.taskapi.repositories;

import java.util.List;
import java.util.UUID;

import com.example.taskapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskapi.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByUser(User user);
}
