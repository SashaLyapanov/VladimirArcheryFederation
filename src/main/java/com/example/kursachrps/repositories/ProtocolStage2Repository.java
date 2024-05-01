package com.example.kursachrps.repositories;

import com.example.kursachrps.models.ProtocolStage2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtocolStage2Repository extends JpaRepository<ProtocolStage2, String> {

    List<ProtocolStage2> findProtocolStage2ByCompetitionIdAndBowTypeIdAndSportsmanSexId(String competitionId, String bowTypeId, String sportsmanSexId);
    List<ProtocolStage2> findProtocolStage2ByCompetitionIdAndBowTypeIdAndSportsmanSexIdAndPlace(String competitionId, String bowTypeId, String sportsmanSexId, int place);
}
