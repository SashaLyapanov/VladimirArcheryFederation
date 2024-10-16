package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.StatusOfCompetition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, String>, JpaSpecificationExecutor<Competition> {

    List<Competition> findAll(Sort sort);

    Optional<Competition> findById(String id);

    List<Competition> findByDate(Date date);

    Competition findByName(String name);

    List<Competition> findByStatus(StatusOfCompetition status, Sort sort);

    List<Competition> findCompetitionByNameAndDateAndCategories(String name, Date date, String categories);

    @Query("SELECT c FROM Competition c WHERE c.status = 'PRESENT'")
    List<Competition> findAllPresent();

    @Query("SELECT c FROM Competition c WHERE c.status = 'PAST'")
    List<Competition> findAllPast(Sort sort);
}
