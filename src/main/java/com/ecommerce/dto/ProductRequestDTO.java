package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Price must be positive")
    private double price;

    @Min(value = 0, message = "Quantity must be zero or positive")
    private int quantityInStock;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
} 