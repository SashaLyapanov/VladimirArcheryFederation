package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewRepository extends JpaRepository<Article, Integer> {

}
