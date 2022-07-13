package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.category.ChildCategoryList;
import com.project.momo.dto.category.ChildCategoryRequest;
import com.project.momo.dto.category.ParentCategoryList;
import com.project.momo.dto.category.ParentCategoryRequest;
import com.project.momo.entity.Category;
import com.project.momo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryById(long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> {
                    throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
                });
    }

    public ParentCategoryList inquireAllParentCategory() {
        return new ParentCategoryList(categoryRepository.findAllByParentId(null));
    }

    public ChildCategoryList inquireAllChildCategory(long parentCategoryId) {
        return new ChildCategoryList(categoryRepository.findAllByParentId(parentCategoryId));
    }

    public void registerNewParentCategory(ParentCategoryRequest parentCategoryRequest) {
        checkDuplicateCategoryName(parentCategoryRequest.getName());
        categoryRepository.save(parentCategoryRequest.toCategory());
    }

    public void registerNewChildCategory(ChildCategoryRequest childCategoryRequest) {
        Category parent = getCategoryById(childCategoryRequest.getParentCategoryId());
        checkDuplicateCategoryName(childCategoryRequest.getName());
        Category newCategory = Category.createChildCategory(childCategoryRequest.getName(), parent);
        categoryRepository.save(newCategory);
    }

    public void checkDuplicateCategoryName(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CATEGORY_NAME);
        }
    }
}
