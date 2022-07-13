package com.project.momo.dto.category;

import com.project.momo.entity.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParentCategoryRequest {
    private String name;
    private String imageUrl;

    public Category toCategory() {
        return Category.createParentCategory(name, imageUrl);
    }
}
