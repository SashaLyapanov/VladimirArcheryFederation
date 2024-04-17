package com.example.kursachrps.service;

import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.models.*;
import com.example.kursachrps.repositories.*;
import com.example.kursachrps.repositories.RegistrAndAuth.CompetitionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final CompetitionMapper competitionMapper;
    private final CompetitionRepositoryImpl competitionRepositoryImpl;

    @Autowired
    public GeneralService(CompetitionRepository competitionRepository,
                          RegionRepository regionRepository,
                          SportsTitleRepository sportsTitleRepository,
                          BowTypeRepository bowTypeRepository,
                          CategoryRepository categoryRepository,
                          CompetitionTypeRepository competitionTypeRepository,
                          SexRepository sexRepository,
                          CompetitionMapper competitionMapper,
                          CompetitionRepositoryImpl competitionRepositoryImpl) {
        this.competitionRepository = competitionRepository;
        this.regionRepository = regionRepository;
        this.sportsTitleRepository = sportsTitleRepository;
        this.bowTypeRepository = bowTypeRepository;
        this.categoryRepository = categoryRepository;
        this.competitionTypeRepository = competitionTypeRepository;
        this.sexRepository = sexRepository;
        this.competitionMapper = competitionMapper;
        this.competitionRepositoryImpl = competitionRepositoryImpl;
    }

    public List<Competition> showAllCompetitions() {
        return competitionRepository.findAll(Sort.by("date"));
    }

    public List<Competition> showCompetitionByDate(Date date) {
        return competitionRepository.findByDate(date);
    }

    public Competition showCompetitionByName(String name) {
        return competitionRepository.findByName(name);
    }

    //Метод выборки соревнований по названию, дате, категории (пока не работает)
    public List<Competition> showCompetitionByNameDateCategory(String name, Date date, String categories) {
        return competitionRepository.findCompetitionByNameAndDateAndCategories(name, date, categories);
    }

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public List<SportsTitle> getAllSportsTitle() {
        return sportsTitleRepository.findAll();
    }

    public List<BowType> getAllBowType() {
        return bowTypeRepository.findAll();
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public List<Sex> getAllSex() {
        return sexRepository.findAll();
    }

    public List<CompetitionType> getAllCompetitionTypes() {
        return competitionTypeRepository.findAll();
    }

    public List<BowType> getAllBowTypeByCompetitionId(String competitionId) {
        return bowTypeRepository.findAllByCompetitionId(competitionId);
    }


    public String getProtocolNameByCompetitionId(String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        assert competition != null;
        return competition.getPdfFile();
    }

    /**
     * Метод для вывода списка соревнований, где status = Past
     */
    public List<Competition> getPastCompetitions() {
        return competitionRepository.findAllPast();
    }


    /**
     * Метод для поиска соревнований по расширенному списку параметров (name, place, type(3D / Target)
     */
    public List<CompetitionDTO> getCompetitionsBySearchParams(String name, String place, String type, Pageable pageable) {
        CompetitionType competitionType = new CompetitionType();
        if (type != null) {
            competitionType = competitionTypeRepository.findById(type).orElse(null);
        }
        List<Competition> competitions = competitionRepositoryImpl.findCompetitionByParams(name, place, competitionType, pageable).getContent();
        return competitionMapper.fromCompetition(competitions);
    }
}
