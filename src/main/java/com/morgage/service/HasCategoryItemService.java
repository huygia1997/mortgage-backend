package com.morgage.service;

import com.morgage.model.HasCategoryItem;
import com.morgage.model.data.CategoryData;
import com.morgage.repository.CategoryRepository;
import com.morgage.repository.HasCategoryItemRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
            categoryData.setName(item.getCategoryItemName());
            categoryData.setValue1(item.getAttribute1Name());
            categoryData.setValue2(item.getAttribute2Name());
            categoryData.setValue3(item.getAttribute3Name());
            categoryData.setValue4(item.getAttribute4Name());
            categoryData.setPaymentTerm(item.getPaymentTerm());
            categoryData.setPaymentType(item.getPaymentType());
            categoryData.setLiquidateAfter(item.getLiquidateAfter());
            categoryData.setCategory(item.getCategory());
            listData.add(categoryData);
        }
        return listData;
    }

    public HasCategoryItem createItem(int shopId, int paymentTerm, int paymentType, int liquidate, int categoryItemId,
                                      String attribute1, String attribute2, String attribute3, String attribute4, String categoryItemName) {
        HasCategoryItem hasCategoryItem = new HasCategoryItem();
        hasCategoryItem.setAttribute1Name(attribute1);
        hasCategoryItem.setAttribute2Name(attribute2);
        hasCategoryItem.setAttribute3Name(attribute3);
        hasCategoryItem.setAttribute4Name(attribute4);
        hasCategoryItem.setIdShop(shopId);
        hasCategoryItem.setIdCategoryItem(categoryItemId);
        hasCategoryItem.setCategoryItemName(categoryItemName);
        hasCategoryItem.setPaymentTerm(paymentTerm);
        hasCategoryItem.setPaymentType(paymentType);
        hasCategoryItem.setLiquidateAfter(liquidate);
        return hasCategoryItemRepository.saveAndFlush(hasCategoryItem);
    }
}
