package com.ecommerce.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String role;
} 