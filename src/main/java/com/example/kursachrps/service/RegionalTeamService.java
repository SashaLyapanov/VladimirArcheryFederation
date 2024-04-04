package com.example.kursachrps.service;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.repositories.SportsmanMainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionalTeamService {

    private final SportsmanMainRepository sportsmanMainRepository;
    private final SportsmanMapper sportsmanMapper;

    public RegionalTeamService(SportsmanMainRepository sportsmanMainRepository, SportsmanMapper sportsmanMapper) {
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.sportsmanMapper = sportsmanMapper;
    }

    public List<SportsmanDTO> getAllSportsman() {
        return sportsmanMapper.fromSportsmanToSportsmanDTO(sportsmanMainRepository.findByIsRegionalTeamSportsman(true).orElse(null));
    }
}
