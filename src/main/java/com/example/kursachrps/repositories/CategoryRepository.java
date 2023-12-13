package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
