package com.example.taskapi.entity;

import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(nullable = false)
    private String title;
    private String description;
    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
