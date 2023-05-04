package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.Judge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JudgeRepository extends JpaRepository<Judge, Integer> {
    Optional<Judge> findByEmail(String email);
}
