package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificatinoRepository extends JpaRepository<Qualification, Integer> {
}
