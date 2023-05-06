package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    List<Competition> findAll();
}
