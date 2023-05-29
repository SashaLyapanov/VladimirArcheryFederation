package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.PersonalExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalExerciseRepository extends JpaRepository<PersonalExercise, Integer> {

    List<PersonalExercise> findAllBySportsmanId(int sportsman_id);

    @Modifying
    @Query(value = "INSERT INTO personal_exercises (sportsman_id, exercise_name, description, photo) VALUES (:sportsmanId, :name, :description , :photo)", nativeQuery = true)
    void addPersonalExerciseInSystem(@Param("name") String name, @Param("description") String description, @Param("photo") String photo, @Param("sportsmanId") int sportsmanId);

}
