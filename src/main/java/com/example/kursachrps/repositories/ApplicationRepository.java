package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

//    @Query("SELECT * FROM Application WHERE competition = ")
//    List<Application> findApplications(int competitionId);

}
