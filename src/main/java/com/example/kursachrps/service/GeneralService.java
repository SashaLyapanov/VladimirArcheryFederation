package com.example.kursachrps.service;

import com.example.kursachrps.Models.Competition;
import com.example.kursachrps.Models.Region;
import com.example.kursachrps.Models.SportsTitle;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.RegionRepository;
import com.example.kursachrps.repositories.SportsTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GeneralService {

    private final CompetitionRepository competitionRepository;
    private final RegionRepository regionRepository;
    private final SportsTitleRepository sportsTitleRepository;

    @Autowired
    public GeneralService (CompetitionRepository competitionRepository,
                           RegionRepository regionRepository,
                           SportsTitleRepository sportsTitleRepository) {
        this.competitionRepository = competitionRepository;
        this.regionRepository = regionRepository;
        this.sportsTitleRepository = sportsTitleRepository;
    }


    @Transactional
    public List<Competition> showAllCompetitions() { return competitionRepository.findAll(Sort.by("date")); }

    @Transactional
    public List<Competition> showCompetitionByDate(Date date) { return competitionRepository.findByDate(date); }


    //Метод выборки соревнований по названию, дате, категории (пока не работает)
    @Transactional
    public List<Competition> showCompetitionByNameDateCategory(String name, Date date, String categories) {
        return competitionRepository.findCompetitionByNameAndDateAndCategories(name, date, categories);
    }


    @Transactional
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Transactional
    public List<SportsTitle> getAllSportsTitle() {
        return sportsTitleRepository.findAll();
    }
}
