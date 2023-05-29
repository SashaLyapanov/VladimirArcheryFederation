package com.example.kursachrps.repositories;

import com.example.kursachrps.Models.New;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewRepository extends JpaRepository<New, Integer> {

}
