package com.example.taskmanager.service;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.exception.ConflictException;
import com.example.taskmanager.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crea un usuario nuevo validando que el email sea único.
     * Lanza ConflictException si el email ya existe.
     */
    @Transactional
    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("User with email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    /**
     * Busca un usuario por id. Lanza NotFoundException si no existe.
     */
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    /**
     * Actualiza nombre/email/password (parcial). Valida unicidad de email si cambia.
     */
    @Transactional
    public User updateUser(Long id, User patch) {
        User existing = getById(id);

        String newEmail = patch.getEmail();
        if (newEmail != null && !newEmail.equals(existing.getEmail())) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new ConflictException("Email already in use: " + newEmail);
            }
            existing.setEmail(newEmail);
        }

        if (patch.getName() != null) {
            existing.setName(patch.getName());
        }

        if (patch.getPassword() != null) {
            existing.setPassword(patch.getPassword());
        }

        return userRepository.save(existing);
    }

    /**
     * Elimina usuario por id. Lanza NotFoundException si no existe.
     */
    @Transactional
    public void deleteUser(Long id) {
        User existing = getById(id);
        userRepository.delete(existing);
    }

    /**
     * Opera útil para pruebas: verifica existencia por email.
     */
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}