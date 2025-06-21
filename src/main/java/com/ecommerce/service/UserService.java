package com.ecommerce.service;

import com.ecommerce.dto.RegistrationRequestDTO;
import com.ecommerce.dto.UserResponseDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(RegistrationRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        User user = new User();
        user.setFullName(requestDTO.getFullName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole("CUSTOMER"); // Default role

        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }

    public UserResponseDTO registerAdmin(RegistrationRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        User user = new User();
        user.setFullName(requestDTO.getFullName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole("ADMIN");

        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    public UserResponseDTO getUserDtoById(Long id) {
        User user = getUserById(id);
        return mapToResponseDTO(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setFullName(userDetails.getFullName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setRole(userDetails.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Update user role
    public User updateUserRole(Long id, String role) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setRole(role);
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Check if email exists
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
} 