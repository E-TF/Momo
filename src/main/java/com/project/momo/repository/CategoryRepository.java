package com.project.momo.repository;

import com.project.momo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(long id);
    boolean existsByName(String name);
    List<Category> findAllByParentId(Long id);
}
