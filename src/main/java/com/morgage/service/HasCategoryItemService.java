package com.morgage.service;

import com.morgage.model.HasCategoryItem;
import com.morgage.model.data.CategoryData;
import com.morgage.repository.CategoryRepository;
import com.morgage.repository.HasCategoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HasCategoryItemService {
    private final HasCategoryItemRepository hasCategoryItemRepository;
    private final CategoryRepository categoryRepository;

    public HasCategoryItemService(HasCategoryItemRepository hasCategoryItemRepository, CategoryRepository categoryRepository) {
        this.hasCategoryItemRepository = hasCategoryItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryData> getAllCategoryItem(int shopId) {

        List<CategoryData> listData = new ArrayList<>();
        List<HasCategoryItem> list = hasCategoryItemRepository.findAllByIdShop(shopId);
        for (HasCategoryItem item : list) {
            CategoryData categoryData = new CategoryData();
            categoryData.setId(item.getIdCategoryItem());
            categoryData.setName(categoryRepository.findById(item.getIdCategoryItem()).getCategoryName());
            categoryData.setValue1(item.getAttribute1Name());
            categoryData.setValue2(item.getAttribute2Name());
            categoryData.setValue3(item.getAttribute3Name());
            categoryData.setValue4(item.getAttribute4Name());
            listData.add(categoryData);
        }
        return listData;
    }
}
