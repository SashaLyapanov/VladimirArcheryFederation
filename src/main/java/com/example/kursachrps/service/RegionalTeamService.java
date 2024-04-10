package com.example.kursachrps.service;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.repositories.SportsmanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionalTeamService {

    private final SportsmanRepository sportsmanRepository;
    private final SportsmanMapper sportsmanMapper;

    public RegionalTeamService(SportsmanRepository sportsmanRepository, SportsmanMapper sportsmanMapper) {
        this.sportsmanRepository = sportsmanRepository;
        this.sportsmanMapper = sportsmanMapper;
    }

    public List<SportsmanDTO> getAllSportsman() {
        return sportsmanMapper.fromSportsmanToSportsmanDTO(sportsmanRepository.findByIsRegionalTeamSportsman(true).orElse(null));
    }
}
