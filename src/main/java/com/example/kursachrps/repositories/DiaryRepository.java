package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.Session;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Session,Integer> {
    List<Session> findSessionBySportsman_Email(String email);
    List<Session> findSessionBySportsman_EmailAndDate(String email, Date date);

}
