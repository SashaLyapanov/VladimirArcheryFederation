package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CoachService coachService;
    private final SportsmanMapper sportsmanMapper;

    @Autowired
    public CoachController (CoachService coachService, SportsmanMapper sportsmanMapper) {
        this.coachService = coachService;
        this.sportsmanMapper = sportsmanMapper;
    }

    /**
     * Метод для выборки всех спортсменов, у которых тренер такой-то (по coach.email)
     */
    @GetMapping("/allMySportsmen")
    List<SportsmanDTO> getAllMySportsmen(@RequestParam String coachEmail) {
        List<SportsmanDTO> sportsmenDTO = sportsmanMapper.fromSportsmanToSportsmanDTO(coachService.findAllMySportsmen(coachEmail));
        return sportsmenDTO;
    }

}
