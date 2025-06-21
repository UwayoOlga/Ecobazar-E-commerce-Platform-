package com.ecommerce.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantityInStock;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;
} 