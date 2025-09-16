package com.example.taskmanager.controller;

import com.example.taskmanager.dto.UserCreateDto;
import com.example.taskmanager.dto.UserResponseDto;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto) {
        User toCreate = new User(dto.getName(), dto.getEmail(), dto.getPassword());
        User created = userService.createUser(toCreate);
        UserResponseDto resp = new UserResponseDto(created.getId(), created.getName(), created.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User u = userService.getById(id);
        UserResponseDto resp = new UserResponseDto(u.getId(), u.getName(), u.getEmail());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDto dto) {
        User patch = new User(dto.getName(), dto.getEmail(), dto.getPassword());
        User updated = userService.updateUser(id, patch);
        UserResponseDto resp = new UserResponseDto(updated.getId(), updated.getName(), updated.getEmail());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}