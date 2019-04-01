package com.morgage.service;

import com.morgage.model.Category;
import com.morgage.model.HasCategoryItem;
import com.morgage.repository.CategoryRepository;
import com.morgage.repository.HasCategoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final HasCategoryItemRepository hasCategoryItemRepository;

    public CategoryService(CategoryRepository categoryRepository, HasCategoryItemRepository hasCategoryItemRepository) {
        this.categoryRepository = categoryRepository;
        this.hasCategoryItemRepository = hasCategoryItemRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return  categoryRepository.save(category);
    }
}
