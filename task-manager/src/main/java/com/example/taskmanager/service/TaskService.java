package com.example.taskmanager.service;

import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Crea una tarea vinculada a un usuario (owner). Lanza NotFoundException si el usuario no existe.
     */
    @Transactional
    public Task createTask(Long ownerId, Task task) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Owner not found with id: " + ownerId));
        task.setOwner(owner);
        return taskRepository.save(task);
    }

    /**
     * Lista todas las tareas de un usuario.
     * Si el usuario no existe lanzamos NotFoundException para mantener consistencia.
     */
    @Transactional(readOnly = true)
    public List<Task> listTasksByOwner(Long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("User not found with id: " + ownerId);
        }
        return taskRepository.findByOwnerId(ownerId);
    }

    /**
     * Obtiene una tarea por id validando que pertenezca al ownerId indicado.
     */
    @Transactional(readOnly = true)
    public Task getTaskForOwner(Long ownerId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + taskId));
        if (task.getOwner() == null || !task.getOwner().getId().equals(ownerId)) {
            throw new NotFoundException("Task with id " + taskId + " not found for user " + ownerId);
        }
        return task;
    }

    /**
     * Actualiza una tarea validando pertenencia. Se aplican cambios parciales (title, description, status).
     */
    @Transactional
    public Task updateTask(Long ownerId, Long taskId, Task patch) {
        Task existing = getTaskForOwner(ownerId, taskId);

        if (patch.getTitle() != null) existing.setTitle(patch.getTitle());
        if (patch.getDescription() != null) existing.setDescription(patch.getDescription());
        if (patch.getStatus() != null) existing.setStatus(patch.getStatus());

        return taskRepository.save(existing);
    }

    /**
     * Elimina una tarea validando pertenencia.
     */
    @Transactional
    public void deleteTask(Long ownerId, Long taskId) {
        Task existing = getTaskForOwner(ownerId, taskId);
        taskRepository.delete(existing);
    }
}