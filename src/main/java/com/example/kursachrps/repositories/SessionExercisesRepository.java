package com.example.kursachrps.repositories;

import com.example.kursachrps.models.SessionExercises;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

@Repository
public interface SessionExercisesRepository extends JpaRepository<SessionExercises, Integer> {

    Optional<SessionExercises> findById(String id);

    @Modifying
    @Query(value = "insert into session_exercises (order_exercise, count_approach, count_repeat, description, session_id,personal_exercises_id)" +
            " values (:orderExercise, :countApproach, :countRepeat, :description, :sessionExercises, :personalExercise)", nativeQuery = true)
    void addExerciseInSession1(@Param("orderExercise") int orderExercise, @Param("countApproach") int countApproach,
                               @Param("countRepeat") int countRepeat, @Param("description") String description, @Param("sessionExercises") String sessionExercises,
                               @Param("personalExercise") String personalExercise);
    @Modifying
    @Query(value = "insert into session_exercises (order_exercise, count_approach, count_repeat, description, session_id, exercises_id)" +
            " values (:orderExercise, :countApproach, :countRepeat, :description, :sessionExercises, :exercise)", nativeQuery = true)
    void addExerciseInSession2(@Param("orderExercise") int orderExercise, @Param("countApproach") int countApproach,
                               @Param("countRepeat") int countRepeat, @Param("description") String description, @Param("sessionExercises") String sessionExercises,
                               @Param("exercise") String exercise);

    List<SessionExercises> findSessionExercisesBySessionExercisesId(String sessionId);

    SessionExercises findSessionExerciseByOrderExerciseAndSessionExercisesId(int orderExercise, String sessionId);

}
