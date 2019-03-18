package com.morgage.service;

import com.morgage.model.Category;
import com.morgage.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryGroupService {
    private final CategoryRepository categoryRepository;

    public CategoryGroupService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
