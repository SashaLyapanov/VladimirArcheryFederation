package com.example.kursachrps.repositories;

import com.example.kursachrps.models.BowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BowTypeRepository extends JpaRepository<BowType, Integer> {


    @Query("SELECT bt FROM BowType bt JOIN bt.competitionList btc where btc.id = :competitionId")
    List<BowType> findAllByCompetitionId(@Param("competitionId") String competitionId);

}
