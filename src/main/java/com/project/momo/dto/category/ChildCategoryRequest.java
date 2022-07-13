package com.project.momo.dto.category;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChildCategoryRequest {
    private String name;
    private long parentCategoryId;
}
