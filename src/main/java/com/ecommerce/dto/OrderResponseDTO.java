package com.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private String status;
    private double totalAmount;
    private List<OrderItemResponseDTO> items;
} 