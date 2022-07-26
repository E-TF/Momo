package com.project.momo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "CATEGORY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue
    Long id;
    @Column(unique = true)
    private String name;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    public static Category createParentCategory(String name, String imageUrl) {
        Category category = new Category();
        category.name = name;
        category.imageUrl = imageUrl;
        return category;
    }

    public static Category createChildCategory(String name, Category parentCategory) {
        Category category = new Category();
        category.name = name;
        category.parent = parentCategory;
        return category;
    }
}
