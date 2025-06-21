package com.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Order must contain at least one item")
    private List<@Valid OrderItemRequestDTO> items;
} 