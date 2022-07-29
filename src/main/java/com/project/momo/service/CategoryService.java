package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.category.*;
import com.project.momo.entity.Category;
import com.project.momo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ParentCategoryResponse> inquireAllParentCategory() {
        return categoryRepository
                .findAllByParentId(null)
                .stream()
                .map(ParentCategoryResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChildCategoryResponse> inquireAllChildCategory(long parentCategoryId) {
        return categoryRepository
                .findAllByParentId(parentCategoryId)
                .stream()
                .map(ChildCategoryResponse::new)
                .collect(Collectors.toList());
    }

    public void registerNewParentCategory(ParentCategoryRequest parentCategoryRequest) {
        checkDuplicateCategoryName(parentCategoryRequest.getName());
        categoryRepository.save(parentCategoryRequest.toCategory());
    }

    public void registerNewChildCategory(ChildCategoryRequest childCategoryRequest) {
        final Category parent = getCategoryById(childCategoryRequest.getParentCategoryId());
        checkCategoryLevelParent(parent);
        checkDuplicateCategoryName(childCategoryRequest.getName());
        final Category newCategory = Category.createChildCategory(childCategoryRequest.getName(), parent);
        categoryRepository.save(newCategory);
    }

    @Transactional(readOnly = true)
    public void checkDuplicateCategoryName(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CATEGORY_NAME);
        }
    }

    private void checkCategoryLevelParent(Category category) {
        if (category.getParent() != null) {
            throw new BusinessException(ErrorCode.NOT_PARENT_CATEGORY);
        }
    }

    public void checkCategoryLevelChild(Category category) {
        if (category.getParent() == null) {
            throw new BusinessException(ErrorCode.NOT_CHILD_CATEGORY);
        }
    }
}
