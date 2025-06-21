package com.ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
} 