package com.example.kursachrps.controllers;

import com.example.kursachrps.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CoachService coachService;

    @Autowired
    public CoachController (CoachService coachService) {
        this.coachService = coachService;
    }



}
