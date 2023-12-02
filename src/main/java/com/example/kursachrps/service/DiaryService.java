package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final ExerciseRepository exerciseRepository;
    private final PersonalExerciseRepository personalExerciseRepository;
    private final SessionExercisesRepository sessionExercisesRepository;
    private final SportsmanMainRepository sportsmanMainRepository;
    private final SessionTypeRepository sessionTypeRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository,
                        ExerciseRepository exerciseRepository, PersonalExerciseRepository personalExerciseRepository, SessionExercisesRepository sessionExercisesRepository, SportsmanMainRepository sportsmanMainRepository, SessionTypeRepository sessionTypeRepository) {
        this.diaryRepository = diaryRepository;
        this.exerciseRepository = exerciseRepository;
        this.personalExerciseRepository = personalExerciseRepository;
        this.sessionExercisesRepository = sessionExercisesRepository;
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.sessionTypeRepository = sessionTypeRepository;
    }

    @Transactional
    public Session showSession(String id) {
        return diaryRepository.findById(id).orElse(null);
    }

    public List<Session> showSessionsBySportsmanEmail(String email) {

        return diaryRepository.findSessionBySportsman_Email(email);
    }

    public List<Session> showSessionsBySportsmanEmailAndData(String email, Date date) {

        return diaryRepository.findSessionBySportsman_EmailAndDate(email,date);
    }

    public Session showAllExercisesById(int id) {
        return diaryRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Session saveSession(Session session) {
        Date date = new Date();
        session.setDateTime(date);

        return diaryRepository.save(session);
    }

    public List<Exercise> getAllSystemExercises() {
        return exerciseRepository.findAll();
    }

    public List<PersonalExercise> getAllPersonalExercises(String sportsman_id) {
        return personalExerciseRepository.findAllBySportsmanId(sportsman_id);
    }

    @Transactional
    public void addExerciseInSession(String sessionId, SessionExercises sessionExercises) {
        Session session = showSession(sessionId);
        sessionExercises.setSessionExercises(session);

//        логика присваивания порядкового номера упражнения в тренировке
        int count = 0;
        if (sessionExercisesRepository.findSessionExercisesBySessionExercisesId(sessionId) == null) {
            count = 1;
            sessionExercises.setOrderExercise(count);
        } else {
            List<SessionExercises> exercisesList = sessionExercisesRepository.findSessionExercisesBySessionExercisesId(sessionId);
            count = exercisesList.size() + 1;
            sessionExercises.setOrderExercise(count);
        }

        if (sessionExercises.getExercise() == null) {
            sessionExercisesRepository.addExerciseInSession1(sessionExercises.getOrderExercise(), sessionExercises.getCountApproach(), sessionExercises.getCountRepeat(), sessionExercises.getDescription(),
                    sessionExercises.getSessionExercises().getId(), sessionExercises.getPersonalExercise().getId());
        } else if (sessionExercises.getPersonalExercise() == null) {
            sessionExercisesRepository.addExerciseInSession2(sessionExercises.getOrderExercise(), sessionExercises.getCountApproach(), sessionExercises.getCountRepeat(), sessionExercises.getDescription(),
                    sessionExercises.getSessionExercises().getId(), sessionExercises.getExercise().getId());
        }
    }

    @Transactional
    public void addPersonalExercise(int sportsmanId, PersonalExercise personalExercise) {
        Sportsman sportsman = sportsmanMainRepository.findById(sportsmanId).orElse(null);
        personalExercise.setSportsman(sportsman);
        personalExerciseRepository.addPersonalExerciseInSystem(personalExercise.getName(), personalExercise.getDescription(), personalExercise.getPhoto(), personalExercise.getSportsman().getId());

    }

    @Transactional
    public void up(String sessionId, String exerciseId) {
        SessionExercises downSessionExercise = sessionExercisesRepository.findById(exerciseId).orElse(null);
        assert downSessionExercise != null;
        int currentOrderExercise = downSessionExercise.getOrderExercise();
        int upperOrderExercises = currentOrderExercise - 1;
        SessionExercises upSessionExercise = sessionExercisesRepository.findSessionExerciseByOrderExerciseAndSessionExercisesId(upperOrderExercises, sessionId);

        upSessionExercise.setOrderExercise(currentOrderExercise);
        downSessionExercise.setOrderExercise(upperOrderExercises);
    }

    @Transactional
    public void down(String sessionId, String exerciseId) {
        SessionExercises upperSessionExercise = sessionExercisesRepository.findById(exerciseId).orElse(null);
        assert upperSessionExercise != null;
        int currentOrderExercise = upperSessionExercise.getOrderExercise();
        int downOrderExercise = currentOrderExercise + 1;
        SessionExercises downSessionExercise = sessionExercisesRepository.findSessionExerciseByOrderExerciseAndSessionExercisesId(downOrderExercise, sessionId);

        downSessionExercise.setOrderExercise(currentOrderExercise);
        upperSessionExercise.setOrderExercise(downOrderExercise);
    }

    @Transactional
    public List<SessionType> getAllSessionType() {
        return sessionTypeRepository.findAll();
    }
}
