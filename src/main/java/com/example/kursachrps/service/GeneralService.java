package com.example.kursachrps.service;

import com.example.kursachrps.Models.Competition;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GeneralService {

    private final CompetitionRepository competitionRepository;
    private final UserMapper userMapper;
    private final CompetitionMapper competitionMapper;

    @Autowired
    public GeneralService (CompetitionRepository competitionRepository,
                           UserMapper userMapper,
                           CompetitionMapper competitionMapper) {
        this.competitionRepository = competitionRepository;
        this.userMapper = userMapper;
        this.competitionMapper = competitionMapper;
    }


    @Transactional
    public List<Competition> showAllCompetitions() { return competitionRepository.findAll(); }

    @Transactional
    public List<Competition> showCompetitionByDate(Date date) { return competitionRepository.findByDate(date); }


    //Метод выборки соревнований по названию, дате, категории (пока не работает)
    @Transactional
    public List<Competition> showCompetitionByNameDateCategory(String name, Date date, String categories) {
        return competitionRepository.findCompetitionByNameAndDateAndCategories(name, date, categories);
    }
}
