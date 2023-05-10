package com.example.kursachrps.service;

import com.example.kursachrps.repositories.CoachMainRepository;
import com.example.kursachrps.repositories.RegistrAndAuth.CoachRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CoachService {

    private CoachMainRepository coachMainRepository;
    private CoachRepository coachRepository;

}
