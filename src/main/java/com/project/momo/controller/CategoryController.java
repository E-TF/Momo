package com.project.momo.controller;

import com.project.momo.dto.category.ChildCategoryList;
import com.project.momo.dto.category.ChildCategoryRequest;
import com.project.momo.dto.category.ParentCategoryList;
import com.project.momo.dto.category.ParentCategoryRequest;
import com.project.momo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ParentCategoryList> inquireAllParentInterestTypes() {
        return ResponseEntity.ok(categoryService.inquireAllParentCategory());
    }

    @GetMapping("/child")
    public ResponseEntity<ChildCategoryList> inquireAllChildInterestTypes(@RequestParam long id) {
        return ResponseEntity.ok(categoryService.inquireAllChildCategory(id));
    }

    @PostMapping
    public ResponseEntity<?> registerNewParentCategory(@RequestBody ParentCategoryRequest parentCategoryRequest) {
        categoryService.registerNewParentCategory(parentCategoryRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("child")
    public ResponseEntity<?> registerNewChildCategory(@RequestBody ChildCategoryRequest childCategoryRequest) {
        categoryService.registerNewChildCategory(childCategoryRequest);
        return ResponseEntity.ok().build();
    }
}
