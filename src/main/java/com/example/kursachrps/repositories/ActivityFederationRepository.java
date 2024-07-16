package com.example.kursachrps.repositories;

import com.example.kursachrps.models.ActivityFederation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityFederationRepository extends JpaRepository<ActivityFederation, String> {

}
