package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskCreateDto {
    @NotBlank
    private String title;
    private String description;

    public TaskCreateDto() {}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}