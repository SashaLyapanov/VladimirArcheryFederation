package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.Competition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    List<Competition> findAll(Sort sort);

    @Override
    Optional<Competition> findById(Integer integer);

    List<Competition> findByDate(Date date);

    List<Competition> findCompetitionByNameAndDateAndCategories(String name, Date date, String categories);

    @Query("SELECT c FROM Competition c WHERE c.status = 'PRESENT'")
    List<Competition> findAllPresent();
}
