package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
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
    private final NewRepository newRepository;

    @Autowired
    public GeneralService (CompetitionRepository competitionRepository,
                           RegionRepository regionRepository,
                           SportsTitleRepository sportsTitleRepository, QualificatinoRepository qualificatinoRepository, BowTypeRepository bowTypeRepository, TeamRepository teamRepository, NewRepository newRepository) {
        this.competitionRepository = competitionRepository;
        this.regionRepository = regionRepository;
        this.sportsTitleRepository = sportsTitleRepository;
        this.qualificatinoRepository = qualificatinoRepository;
        this.bowTypeRepository = bowTypeRepository;
        this.teamRepository = teamRepository;
        this.newRepository = newRepository;
    }


    @Transactional
    public List<Competition> showAllCompetitions() { return competitionRepository.findAll(Sort.by("date")); }

    @Transactional
    public List<Competition> showCompetitionByDate(Date date) { return competitionRepository.findByDate(date); }

    @Transactional
    public Competition showCompetitionByName(String name) { return competitionRepository.findByName(name); }


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


    @Transactional
    public List<BowType> getAllBowTypeByCompetitionId(String competitionId) { return bowTypeRepository.findAllByCompetitionId(competitionId); }


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


    public List<New> getAllNews() {
        return newRepository.findAll();
    }

    public New getNew(int newId) {
        return newRepository.findById(newId).orElse(null);
    }


//    public List<Competition> searchCompetitions(String competitionName, Integer bowType, Integer competitinoCategory) {
//
//        if (competitionName != null && bowType != null && competitinoCategory != null) {
//            competitionRepository.findCompetitionByNameAndBowTypeAndCategories(competitionName, bowType, competitinoCategory);
//        } else if (competitionName != null && bowType  == null && competitinoCategory == null) {
//            competitionRepository.findCompetitionByName(competitionName);
//        } else if (competitionName != null && bowType != null && competitinoCategory == null) {
//            competitionRepository.findCompetitionByNameAndBowType(competitionName, bowType);
//        } else if (competitionName != null && bowType != null && competitinoCategory != null) {
//            return null;
//        }
//
//        return null;
//    }
}
