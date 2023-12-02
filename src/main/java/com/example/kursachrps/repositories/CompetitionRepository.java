package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Competition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    List<Competition> findAll(Sort sort);

    Optional<Competition> findById(String id);

    List<Competition> findByDate(Date date);

    Competition findByName(String name);

    List<Competition> findCompetitionByNameAndDateAndCategories(String name, Date date, String categories);

    @Query("SELECT c FROM Competition c WHERE c.status = 'PRESENT'")
    List<Competition> findAllPresent();

    @Query("SELECT c FROM Competition c WHERE c.status = 'PAST'")
    List<Competition> findAllPast();

//    @Query
//    List<Competition> findCompetitionByNameAndBowTypeAndCategories(@Param("name") String competitionName, @Param("bowType") Integer bowType, @Param("category") Integer competitinoCategory);
//
//    List<Competition> findCompetitionByName(String competitionName);

//    @Query("SELECT c FROM Competition c WHERE c.name = :name and c.bowTypeList = :bow")
//    List<Competition> findCompetitionByNameAndBowType(@Param("name") String competitionName, Integer bowType);
}
