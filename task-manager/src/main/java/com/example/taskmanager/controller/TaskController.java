package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskCreateDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.dto.TaskUpdateDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) { this.taskService = taskService; }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@PathVariable Long userId,
                                                      @Valid @RequestBody TaskCreateDto dto) {
        Task toCreate = new Task(dto.getTitle(), dto.getDescription(), null);
        Task created = taskService.createTask(userId, toCreate);
        TaskResponseDto resp = toDto(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> listTasks(@PathVariable Long userId) {
        List<Task> tasks = taskService.listTasksByOwner(userId);
        List<TaskResponseDto> resp = tasks.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long userId, @PathVariable Long taskId) {
        Task task = taskService.getTaskForOwner(userId, taskId);
        return ResponseEntity.ok(toDto(task));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long userId, @PathVariable Long taskId,
                                                      @Valid @RequestBody TaskUpdateDto dto) {
        Task patch = new Task();
        patch.setTitle(dto.getTitle());
        patch.setDescription(dto.getDescription());
        patch.setStatus(dto.getStatus());
        Task updated = taskService.updateTask(userId, taskId, patch);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long userId, @PathVariable Long taskId) {
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

    private TaskResponseDto toDto(Task t) {
        return new TaskResponseDto(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus(),
                t.getCreatedAt(),
                t.getUpdatedAt(),
                t.getOwner() != null ? t.getOwner().getId() : null
        );
    }
}