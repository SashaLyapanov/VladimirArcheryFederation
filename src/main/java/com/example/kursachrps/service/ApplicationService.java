package com.example.kursachrps.service;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    /**
     * Метод для выборки всех заявок для определенных соревнований
     */
    @Transactional
    public List<Application> getApplicationsForCompetition(String competitionId) {
        return applicationRepository.findApplicationByCompetitionId(competitionId);
    }

    /**
     * Метод для выборки всех спортсменов из списка заявок
     */
    @Transactional
    public List<SportsmanDTO> getSportsmenFromApplications(List<ApplicationDTO> applicationDTOList) {
        List<SportsmanDTO> sportsmanDTOList = new ArrayList<>();

        for(ApplicationDTO applicationDTO: applicationDTOList) {
            sportsmanDTOList.add(applicationDTO.getSportsman());
        }
        return sportsmanDTOList;
    }

    /**
     * Выборка всех собственных заявок
     */
    @Transactional
    public List<Application> getMyApplications(String myId) {
        List<Application> applicationsOfSportsman = applicationRepository.findApplicationBySportsmanId(myId);
        List<Application> applicationOfCoach = applicationRepository.findApplicationByCoachId(myId);
        if (applicationsOfSportsman.isEmpty()) {
            return applicationOfCoach;
        } else {
            return applicationsOfSportsman;
        }
    }


    /**
     * Метод валидирующий подачу заявки спортсменом и тренером.
     */
    public boolean checkRegistrationInCompetition(String competitionId, String participantId) {
        Application sportsmanApplication = applicationRepository.findApplicationBySportsmanAndCompetition(competitionId, participantId);
        Application coachApplication = applicationRepository.findApplicationByCoachAndCompetition(competitionId, participantId);

        if (sportsmanApplication == null && coachApplication == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkRegistrationInCompetitionByParticipantEmail(String competitionId, String participantEmail) {
        Application sportsmanApplication = applicationRepository.findApplicationBySportsmanEmailAndCompetition(competitionId, participantEmail);
        Application coachApplication = applicationRepository.findApplicationByCoachEmailAndCompetition(competitionId, participantEmail);

        if (sportsmanApplication == null && coachApplication == null) {
            return true;
        } else {
            return false;
        }
    }

}


















