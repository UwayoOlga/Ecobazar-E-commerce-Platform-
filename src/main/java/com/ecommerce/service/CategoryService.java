package com.ecommerce.service;

import com.ecommerce.dto.CategoryRequestDTO;
import com.ecommerce.dto.CategoryResponseDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        return mapToResponseDTO(savedCategory);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryDtoById(Long id) {
        Category category = getCategoryById(id);
        return mapToResponseDTO(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = getCategoryById(id);
        category.setName(categoryRequestDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return mapToResponseDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
} 