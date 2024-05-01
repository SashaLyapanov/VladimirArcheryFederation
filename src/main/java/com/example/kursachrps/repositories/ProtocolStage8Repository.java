package com.example.kursachrps.repositories;

import com.example.kursachrps.models.ProtocolStage8;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtocolStage8Repository extends JpaRepository<ProtocolStage8, String> {

    List<ProtocolStage8> findProtocolStage8ByCompetitionIdAndBowTypeIdAndSportsmanSexId(String competitionId, String bowTypeId, String sportsmanSexId);
}
