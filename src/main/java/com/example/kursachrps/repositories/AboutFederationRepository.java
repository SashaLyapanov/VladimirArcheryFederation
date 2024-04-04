package com.example.kursachrps.repositories;

import com.example.kursachrps.models.AboutFederation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AboutFederationRepository extends JpaRepository<AboutFederation, String> {

    Optional<AboutFederation> findById(String id);
}
