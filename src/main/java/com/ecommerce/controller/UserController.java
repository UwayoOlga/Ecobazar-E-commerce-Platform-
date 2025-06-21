package com.ecommerce.controller;

import com.ecommerce.dto.RegistrationRequestDTO;
import com.ecommerce.dto.UserResponseDTO;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        UserResponseDTO registeredUser = userService.registerUser(requestDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-admin")
    public ResponseEntity<UserResponseDTO> registerAdmin(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        UserResponseDTO registeredUser = userService.registerAdmin(requestDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
} 