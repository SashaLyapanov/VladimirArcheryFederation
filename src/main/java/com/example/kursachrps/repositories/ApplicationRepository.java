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
    List<Application> findApplicationByCompetitionId(@Param("competitionId") int competitionId);

    @Query("SELECT a FROM Application a WHERE a.sportsman.id = :sportsmanId")
    List<Application> findApplicationBySportsmanId(@Param("sportsmanId") int sportsmanId);

    @Query("SELECT a FROM Application a WHERE a.coach.id = :coachId")
    List<Application> findApplicationByCoachId(@Param("coachId") int coachId);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.sportsman.id = :sportsmanId")
    Application findApplicationBySportsmanAndCompetition(@Param("competitionId") int competitionId, @Param("sportsmanId") int sportsmanId);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.coach.id = :coachId")
    Application findApplicationByCoachAndCompetition(@Param("competitionId") int competitionId, @Param("coachId") int coachId);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.sportsman.email = :email")
    Application findApplicationBySportsmanAndCompetition(@Param("competitionId") int competitionId, @Param("email") String email);

    @Query("SELECT a FROM Application a WHERE a.competition.id = :competitionId and a.coach.email = :email")
    Application findApplicationByCoachAndCompetition(@Param("competitionId") int competitionId, @Param("email") String email);

}
