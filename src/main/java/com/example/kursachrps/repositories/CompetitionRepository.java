package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.Competition;
import jakarta.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    List<Competition> findAll();

    List<Competition> findByDate(Date date);

    List<Competition> findCompetitionByNameAndDateAndCategories(String name, Date date, String categories);
}
