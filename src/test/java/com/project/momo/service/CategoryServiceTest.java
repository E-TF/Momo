package com.project.momo.service;

import com.project.momo.common.exception.BusinessException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.dto.category.ChildCategoryRequest;
import com.project.momo.dto.category.ParentCategoryRequest;
import com.project.momo.entity.Category;
import com.project.momo.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void beforeEach() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    @DisplayName("하위 카테고리에 새로운 하위 카테고리를 등록하면 NOT_PARENT_CATEGORY(BusinessException)이 발생한다.")
    void registerNewChildCategoryFailTest() {
        //given
        long CHILD_CATEGORY_ID = 0L;
        Category parentCategory = new Category(
                1L,
                "TEST_NAME",
                "TEST_IMAGE_URL",
                null);
        Category childCategory = new Category(CHILD_CATEGORY_ID, "TEST_CHILD_NAME", null, parentCategory);
        when(categoryRepository.findById(CHILD_CATEGORY_ID)).thenReturn(Optional.of(childCategory));
        ChildCategoryRequest childCategoryRequest = new ChildCategoryRequest(
                "NEW_CATEGORY_NAME", CHILD_CATEGORY_ID);

        //when & then
        try {
            categoryService.registerNewChildCategory(childCategoryRequest);
        } catch (BusinessException e) {
            Assertions.assertEquals(e.getErrorCode(), ErrorCode.NOT_PARENT_CATEGORY);
            return;
        }
        fail();
    }

    @Test
    @DisplayName("중복된 카테고리의 이름을 등록하면 DUPLICATED_CATEGORY_NAME(BusinessException)이 발생한다.")
    void registerNewParentCategoryFailTest() {
        //given
        String DUPLICATED_CATEGORY_NAME = "TEST_CATEGORY_NAME";
        when(categoryRepository.existsByName(DUPLICATED_CATEGORY_NAME)).thenReturn(true);

        //when & then
        try {
            categoryService
                    .registerNewParentCategory(new ParentCategoryRequest(DUPLICATED_CATEGORY_NAME, "IMAGE_URL"));
        } catch (BusinessException e) {
            assertEquals(e.getErrorCode(), ErrorCode.DUPLICATED_CATEGORY_NAME);
            return;
        }
        fail();
    }
}
