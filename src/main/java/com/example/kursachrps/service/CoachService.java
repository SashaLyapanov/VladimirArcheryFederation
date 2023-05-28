package com.example.kursachrps.service;

import com.example.kursachrps.Models.Coach;
import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.repositories.CoachMainRepository;
import com.example.kursachrps.repositories.RegistrAndAuth.CoachRepository;
import com.example.kursachrps.repositories.SportsmanMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CoachService {

    private CoachMainRepository coachMainRepository;
    private CoachRepository coachRepository;
    private SportsmanMainRepository sportsmanMainRepository;

    @Autowired
    public CoachService(CoachMainRepository coachMainRepository,
                        CoachRepository coachRepository,
                        SportsmanMainRepository sportsmanMainRepository) {
        this.coachMainRepository = coachMainRepository;
        this.coachRepository = coachRepository;
        this.sportsmanMainRepository = sportsmanMainRepository;
    }


    public List<Sportsman> findAllMySportsmen(String coachEmail) {
        Coach coach = coachMainRepository.findByEmail(coachEmail).orElse(null);
        System.out.println(coach.getId());
        assert coach != null;
        return sportsmanMainRepository.findAllSportsmanByPersonalCoach(coach.getId());
    }
}
