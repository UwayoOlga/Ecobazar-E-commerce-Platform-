package com.ecommerce.repository;
 
import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by category ID
    List<Product> findByCategoryId(Long categoryId);

    // Find products by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Find products by price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Find products in stock
    List<Product> findByQuantityInStockGreaterThan(Integer quantity);

    // Find products by category name
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    // Find product by name (exact match)
    Optional<Product> findByName(String name);

    // Find products with stock less than threshold
    List<Product> findByQuantityInStockLessThan(Integer threshold);

    // Find products by price less than
    List<Product> findByPriceLessThan(Double price);

    // Find products by price greater than
    List<Product> findByPriceGreaterThan(Double price);
}
