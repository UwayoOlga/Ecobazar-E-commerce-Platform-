package com.ecommerce.service;

import com.ecommerce.dto.ProductRequestDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    // Create a new product
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Category category = categoryService.getCategoryById(dto.getCategoryId());
        Product product = mapToProduct(dto, category);
        Product saved = productRepository.save(product);
        return mapToResponseDTO(saved);
    }

    // Get all products
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get product by ID
    public Optional<ProductResponseDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::mapToResponseDTO);
    }

    // Update product
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Category category = categoryService.getCategoryById(dto.getCategoryId());
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setQuantityInStock(dto.getQuantityInStock());
            product.setImageUrl(dto.getImageUrl());
            product.setCategory(category);
            Product updated = productRepository.save(product);
            return mapToResponseDTO(updated);
        }
        return null;
    }

    // Delete product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Update product stock
    public Product updateStock(Long id, int quantity) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            existingProduct.setQuantityInStock(quantity);
            return productRepository.save(existingProduct);
        }
        return null;
    }

    // Find products by category
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Search products by name
    public List<ProductResponseDTO> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Check product availability
    public boolean isProductAvailable(Long id, int requestedQuantity) {
        Optional<Product> product = productRepository.findById(id);
        return product.isPresent() && product.get().getQuantityInStock() >= requestedQuantity;
    }

    // --- Mapping methods ---
    private Product mapToProduct(ProductRequestDTO dto, Category category) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantityInStock(dto.getQuantityInStock());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);
        return product;
    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantityInStock(product.getQuantityInStock());
        dto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
}
