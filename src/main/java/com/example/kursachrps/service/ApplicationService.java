package com.example.kursachrps.service;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Application> getApplicationsForCompetition(String competitionId) {
        return applicationRepository.findApplicationByCompetitionId(competitionId);
    }

    /**
     * Метод для выборки всех заявок для опредедленных соревнований и типа лука
     */
    public List<Application> getApplicationsForCompetitionAndBowType(String competitionId, String bowTypeName) {
        return applicationRepository.findApplicationByCompetitionIdAndBowTypeBowTypeName(competitionId, bowTypeName);
    }

    /**
     * Метод для выборки всех спортсменов из списка заявок
     */
    public List<SportsmanDTO> getSportsmenDTOFromApplications(List<ApplicationDTO> applicationDTOList) {
        List<SportsmanDTO> sportsmanDTOList = new ArrayList<>();

        for(ApplicationDTO applicationDTO: applicationDTOList) {
            sportsmanDTOList.add(applicationDTO.getSportsman());
        }
        return sportsmanDTOList;
    }


    public List<Sportsman> getSportsmenFromApplications(List<Application> applicationList) {
        List<Sportsman> sportsmanList = new ArrayList<>();

        for(Application application: applicationList) {
            sportsmanList.add(application.getSportsman());
        }
        return sportsmanList;
    }

    /**
     * Выборка всех собственных заявок
     */
    public List<Application> getMyApplications(String myId) {
        List<Application> applicationsOfSportsman = applicationRepository.findApplicationBySportsmanId(myId);
        if (applicationsOfSportsman.isEmpty()) {
            return null;
        } else {
            return applicationsOfSportsman;
        }
    }

    /**
     * Получение заявки
     */
    public void deleteMyApplication(String sportsmanId, String competitionId) {
        Application application = applicationRepository.findApplicationBySportsmanIdAndCompetitionId(sportsmanId, competitionId);
        applicationRepository.delete(application);
    }


    /**
     * Метод валидирующий подачу заявки спортсменом и тренером.
     */
    public boolean checkRegistrationInCompetition(String competitionId, String participantId) {
        Application sportsmanApplication = applicationRepository.findApplicationBySportsmanIdAndCompetitionId(participantId, competitionId);
        return sportsmanApplication == null;
    }

    public boolean checkRegistrationInCompetitionByParticipantEmail(String competitionId, String participantEmail) {
        Application sportsmanApplication = applicationRepository.findApplicationBySportsmanEmailAndCompetitionId(participantEmail, competitionId);
        return sportsmanApplication == null;
    }

}


















