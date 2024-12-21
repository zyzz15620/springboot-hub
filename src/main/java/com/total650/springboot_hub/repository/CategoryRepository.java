package com.total650.springboot_hub.repository;

import com.total650.springboot_hub.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
