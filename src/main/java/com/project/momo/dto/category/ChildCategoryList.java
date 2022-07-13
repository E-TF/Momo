package com.project.momo.dto.category;

import com.project.momo.entity.Category;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChildCategoryList {
    private List<ChildCategoryResponse> categoryList = new ArrayList<>();

    public ChildCategoryList(List<Category> categories) {
        categories.stream().map(ChildCategoryResponse::new).forEach(categoryList::add);
    }
}
