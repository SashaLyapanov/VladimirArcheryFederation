package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId")
    List<Application> findApplicationByCompetitionId(@Param("competitionId") int competitionId);

    @Query("SELECT a FROM Application a WHERE a.sportsman.id = :sportsmanId")
    List<Application> findApplicationBySportsmanId(@Param("sportsmanId") int sportsmanId);
}
