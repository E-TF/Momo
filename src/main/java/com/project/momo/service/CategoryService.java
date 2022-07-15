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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category getCategoryById(long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> {
                    throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
                });
    }

    @Transactional(readOnly = true)
    public ParentCategoryList inquireAllParentCategory() {
        return new ParentCategoryList(categoryRepository.findAllByParentId(null));
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public void checkDuplicateCategoryName(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CATEGORY_NAME);
        }
    }
}
