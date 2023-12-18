package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import com.example.kursachrps.repositories.*;
import com.example.kursachrps.repositories.RegistrAndAuth.CompetitionTypeRepository;
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
    private final BowTypeRepository bowTypeRepository;
    private final CategoryRepository categoryRepository;
    private final CompetitionTypeRepository competitionTypeRepository;
    private final SexRepository sexRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public GeneralService (CompetitionRepository competitionRepository,
                           RegionRepository regionRepository,
                           SportsTitleRepository sportsTitleRepository,
                           BowTypeRepository bowTypeRepository,
                           CategoryRepository categoryRepository,
                           CompetitionTypeRepository competitionTypeRepository,
                           SexRepository sexRepository,
                           ArticleRepository articleRepository) {
        this.competitionRepository = competitionRepository;
        this.regionRepository = regionRepository;
        this.sportsTitleRepository = sportsTitleRepository;
        this.bowTypeRepository = bowTypeRepository;
        this.categoryRepository = categoryRepository;
        this.competitionTypeRepository = competitionTypeRepository;
        this.sexRepository = sexRepository;
        this.articleRepository = articleRepository;
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
    public List<BowType> getAllBowType() {
        return bowTypeRepository.findAll();
    }

    @Transactional
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Transactional
    public List<Sex> getAllSex() {
        return sexRepository.findAll();
    }

    @Transactional
    public List<CompetitionType> getAllCompetitionTypes() {
        return competitionTypeRepository.findAll();
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
