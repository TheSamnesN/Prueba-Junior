package com.example.taskmanager.dto;

import com.example.taskmanager.model.TaskStatus;

import java.time.Instant;

public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Long ownerId;

    public TaskResponseDto() {}

    public TaskResponseDto(Long id, String title, String description, TaskStatus status,
                           Instant createdAt, Instant updatedAt, Long ownerId) {
        this.id = id; this.title = title; this.description = description;
        this.status = status; this.createdAt = createdAt; this.updatedAt = updatedAt;
        this.ownerId = ownerId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Long getOwnerId() { return ownerId; }
}