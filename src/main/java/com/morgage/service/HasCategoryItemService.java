package com.morgage.service;

import com.morgage.repository.HasCategoryItemRepository;
import org.springframework.stereotype.Service;

@Service
public class HasCategoryItemService {
    private final HasCategoryItemRepository hasCategoryItemRepository;

    public HasCategoryItemService(HasCategoryItemRepository hasCategoryItemRepository) {
        this.hasCategoryItemRepository = hasCategoryItemRepository;
    }
}
