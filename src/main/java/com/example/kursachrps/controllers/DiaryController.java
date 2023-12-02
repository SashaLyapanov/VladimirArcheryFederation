package com.example.kursachrps.controllers;

import com.example.kursachrps.models.PersonalExercise;
import com.example.kursachrps.models.Session;
import com.example.kursachrps.models.SessionExercises;
import com.example.kursachrps.dto.diary.*;
import com.example.kursachrps.mapper.DiaryMapper;
import com.example.kursachrps.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sportsman/diary")
public class DiaryController {
    private final DiaryService diaryService;
    private final DiaryMapper diaryMapper;

    @Autowired
    public DiaryController(DiaryService diaryService, DiaryMapper diaryMapper) {
        this.diaryService = diaryService;
        this.diaryMapper = diaryMapper;
    }

    /**
     * Метод для вывода всех тренировок спортсмена
     */
    @GetMapping("allSportsmanSessions")
    public List<SessionDTO> getAllSessions(@RequestParam String email) {
        List<Session> sessions = diaryService.showSessionsBySportsmanEmail(email);
        List<SessionDTO> dto = diaryMapper.fromSession(sessions);
        return dto;
    }

    /**
     * Метод для вывода всех тренировок спортсмена по дате (для дневника)
     */
    @GetMapping("allSportsmanSessionsByDay")
    public List<SessionDTO> getAllSessionsByDate(@RequestParam String email, @RequestParam Date date) {
        List<Session> sessions = diaryService.showSessionsBySportsmanEmailAndData(email,date);
        List<SessionDTO> dto = diaryMapper.fromSession(sessions);
        return dto;
    }

    /**
     * Получение всех упражнений для определенной тренировки (по факту (выборка из таблицы session_exercises по session_id))
     */
    @GetMapping("getSessionExercises")
    public GetExercisesDTO getExercisesBySessionId(@RequestParam int id) {
        Session sessionExercises = new Session();
        sessionExercises = diaryService.showAllExercisesById(id);
        GetExercisesDTO dto = diaryMapper.fromExercises(sessionExercises);
        return dto;
    }

    /**
     * Метод созднаия тренировки
     */
    @PostMapping("createSession")
    public Session createSession(@RequestBody CreateSessionDTO createSessionDTO) {
        Session session = diaryMapper.convert(createSessionDTO);
        session = diaryService.saveSession(session);
        return session;
    }

    /**
     * Метод для получения всех встроенных в систему упражнений
     */
    @GetMapping("getAllSystemExercises")
    public List<ExerciseDTO> getAllSystemExercises() {
        List<ExerciseDTO> exerciseDTOList = diaryMapper.fromExerciseList(diaryService.getAllSystemExercises());
        return exerciseDTOList;
    }

    /**
     * Метод для получения всех собственных упражнений
     */
    @GetMapping("getAllPersonExercises")
    public List<PersonalExerciseDTO> getAllPersonalExercises(@RequestParam String sportsman_id) {
        List<PersonalExerciseDTO> personalExercises = diaryMapper.fromPersonalExercise(diaryService.getAllPersonalExercises(sportsman_id));
        return personalExercises;
    }

    /**
     * Метод для внесения упражнения в тренировку
     */
    @PostMapping("addExerciseToSessionById")
    public void addExercise(@RequestParam String sessionId, @RequestBody SessionExercisesDTO sessionExercisesDTO) {
        SessionExercises sessionExercises = diaryMapper.fromSessionExercisesDTO(sessionExercisesDTO);

        diaryService.addExerciseInSession(sessionId, sessionExercises);
    }

    /**
     * Метод для добавления PersonalExercise
     */
    @PostMapping("addPersonalExercise")
    public void addPersonalExercise(@RequestParam int sportsmanId, @RequestBody PersonalExerciseDTO personalExerciseDTO) {
        PersonalExercise personalExercise = diaryMapper.fromPersonalExerciseDTO(personalExerciseDTO);
        diaryService.addPersonalExercise(sportsmanId, personalExercise);
    }

    /**
     * Метод для смены упражнений в тренировке (нижнее поднять)
     */
    @PostMapping("upExerciseInSession")
    public void up(@RequestParam String sessionId, @RequestParam String exerciseId) {
        diaryService.up(sessionId, exerciseId);
    }

    /**
     * Метод для смены упражнений в тренировке (верхнее опустить)
     */
    @PostMapping("downExerciseInSession")
    public void down(@RequestParam String sessionId, @RequestParam String exerciseId) {
        diaryService.down(sessionId, exerciseId);
    }

    /**
     * Метод для получения всех типов тренировок
     */
    @GetMapping("allSessionType")
    public List<SessionTypeDTO> getAllSessionType() {
        return diaryMapper.fromSessionType(diaryService.getAllSessionType());
    }


}
