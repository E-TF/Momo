package com.project.momo.dto.category;

import com.project.momo.entity.Category;
import lombok.Getter;

@Getter
public class ParentCategoryResponse {
    private long id;
    private String name;
    private String imageUrl;

    public ParentCategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.imageUrl = category.getImageUrl();
    }
}
