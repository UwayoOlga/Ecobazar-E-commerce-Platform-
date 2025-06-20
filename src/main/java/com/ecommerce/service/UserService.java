package com.ecommerce.service;

import com.ecommerce.model.user;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public user createUser(user user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<user> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<user> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get user by email
    public Optional<user> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Update user
    public user updateUser(Long id, user userDetails) {
        Optional<user> user = userRepository.findById(id);
        if (user.isPresent()) {
            user existingUser = user.get();
            existingUser.setFullName(userDetails.getFullName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setRole(userDetails.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Update user role
    public user updateUserRole(Long id, String role) {
        Optional<user> user = userRepository.findById(id);
        if (user.isPresent()) {
            user existingUser = user.get();
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