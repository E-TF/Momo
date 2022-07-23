package com.project.momo.dto.category;

import com.project.momo.entity.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParentCategoryRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String imageUrl;

    public Category toCategory() {
        return Category.createParentCategory(name, imageUrl);
    }
}
