package com.example.kursachrps.service;

import com.example.kursachrps.Models.*;
import com.example.kursachrps.repositories.*;
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
    private final QualificatinoRepository qualificatinoRepository;
    private final BowTypeRepository bowTypeRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public GeneralService (CompetitionRepository competitionRepository,
                           RegionRepository regionRepository,
                           SportsTitleRepository sportsTitleRepository, QualificatinoRepository qualificatinoRepository, BowTypeRepository bowTypeRepository, TeamRepository teamRepository) {
        this.competitionRepository = competitionRepository;
        this.regionRepository = regionRepository;
        this.sportsTitleRepository = sportsTitleRepository;
        this.qualificatinoRepository = qualificatinoRepository;
        this.bowTypeRepository = bowTypeRepository;
        this.teamRepository = teamRepository;
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

    @Transactional
    public List<Qualification> getAllQualifications() {
        return qualificatinoRepository.findAll();
    }

    @Transactional
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Transactional
    public List<BowType> getAllBowType() {
        return bowTypeRepository.findAll();
    }


    public String getProtocolNameByCompetitionId(int competitionId) {
        Competition competition  = competitionRepository.findById(competitionId).orElse(null);
        assert competition != null;
        return competition.getPdfFile();
    }

    /**
     * Метод для вывода списка соревнований, где status = Past
     */
    public List<Competition> getPresentCompetitions() {
        return competitionRepository.findAllPast();
    }
}
