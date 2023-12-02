package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId")
    List<Application> findApplicationByCompetitionId(@Param("competitionId") String competitionId);

    @Query("SELECT a FROM Application a WHERE a.sportsman.id = :sportsmanId")
    List<Application> findApplicationBySportsmanId(@Param("sportsmanId") String sportsmanId);

    @Query("SELECT a FROM Application a WHERE a.coach.id = :coachId")
    List<Application> findApplicationByCoachId(@Param("coachId") String coachId);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.sportsman.id = :sportsmanId")
    Application findApplicationBySportsmanAndCompetition(@Param("competitionId") String competitionId, @Param("sportsmanId") String sportsmanId);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.coach.id = :coachId")
    Application findApplicationByCoachAndCompetition(@Param("competitionId") String competitionId, @Param("coachId") String coachId);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.sportsman.email = :email")
    Application findApplicationBySportsmanEmailAndCompetition(@Param("competitionId") String competitionId, @Param("email") String email);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.coach.email = :email")
    Application findApplicationByCoachEmailAndCompetition(@Param("competitionId") String competitionId, @Param("email") String email);

}
