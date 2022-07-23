package com.project.momo.controller;

import com.project.momo.dto.category.ChildCategoryRequest;
import com.project.momo.dto.category.ChildCategoryResponse;
import com.project.momo.dto.category.ParentCategoryRequest;
import com.project.momo.dto.category.ParentCategoryResponse;
import com.project.momo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<ParentCategoryResponse>> inquireAllParentInterestTypes() {
        return ResponseEntity.ok(categoryService.inquireAllParentCategory());
    }

    @GetMapping("/category/child")
    public ResponseEntity<List<ChildCategoryResponse>> inquireAllChildInterestTypes(@RequestParam final long id) {
        return ResponseEntity.ok(categoryService.inquireAllChildCategory(id));
    }

    @PostMapping("/admin/category")
    public ResponseEntity registerNewParentCategory(
            @RequestBody @Valid final ParentCategoryRequest parentCategoryRequest
    ) {
        categoryService.registerNewParentCategory(parentCategoryRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/category/child")
    public ResponseEntity registerNewChildCategory(
            @RequestBody @Valid final ChildCategoryRequest childCategoryRequest
    ) {
        categoryService.registerNewChildCategory(childCategoryRequest);
        return ResponseEntity.ok().build();
    }
}
