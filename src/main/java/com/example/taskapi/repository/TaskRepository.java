package com.example.taskapi.repository;

import java.util.List;
import java.util.UUID;

import com.example.taskapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskapi.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByUser(User user);
}
