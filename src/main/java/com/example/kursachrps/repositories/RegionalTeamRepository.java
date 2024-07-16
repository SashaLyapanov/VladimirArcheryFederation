package com.example.kursachrps.repositories;

import com.example.kursachrps.models.RegionalTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalTeamRepository extends JpaRepository<RegionalTeam, String> {

}
