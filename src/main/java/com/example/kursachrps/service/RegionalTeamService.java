package com.example.kursachrps.service;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.models.RegionalTeam;
import com.example.kursachrps.repositories.RegionalTeamRepository;
import com.example.kursachrps.repositories.SportsmanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionalTeamService {

    private final SportsmanRepository sportsmanRepository;
    private final SportsmanMapper sportsmanMapper;
    private final RegionalTeamRepository regionalTeamRepository;

    public RegionalTeamService(SportsmanRepository sportsmanRepository, SportsmanMapper sportsmanMapper, RegionalTeamRepository regionalTeamRepository) {
        this.sportsmanRepository = sportsmanRepository;
        this.sportsmanMapper = sportsmanMapper;
        this.regionalTeamRepository = regionalTeamRepository;
    }

    public List<SportsmanDTO> getAllSportsman() {
        return sportsmanMapper.fromSportsmanToSportsmanDTO(sportsmanRepository.findByIsRegionalTeamSportsman(true).orElse(null));
    }

    public List<String> getAllRegionalTeamFiles() {
        List<RegionalTeam> list =  regionalTeamRepository.findAll();
        List<String> response = new ArrayList<>();
        for (RegionalTeam team: list) {
            response.add(team.getFileName());
        }
        return response;
    }
}
