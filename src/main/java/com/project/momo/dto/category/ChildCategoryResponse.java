package com.project.momo.dto.category;

import com.project.momo.entity.Category;
import lombok.Getter;

@Getter
public class ChildCategoryResponse {
    private long id;
    private String name;

    public ChildCategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
