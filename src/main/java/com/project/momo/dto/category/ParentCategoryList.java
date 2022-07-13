package com.project.momo.dto.category;

import com.project.momo.entity.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParentCategoryList {
    private List<ParentCategoryResponse> categoryList = new ArrayList<>();

    public ParentCategoryList(List<Category> categories) {
        categories.stream().map(ParentCategoryResponse::new).forEach(categoryList::add);
    }
}
