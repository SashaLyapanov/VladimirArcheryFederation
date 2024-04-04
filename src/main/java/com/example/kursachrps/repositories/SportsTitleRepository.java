package com.example.kursachrps.repositories;

import com.example.kursachrps.models.SportsTitle;
import com.example.kursachrps.utils.CommonSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SportsTitleRepository extends JpaRepository<SportsTitle, String>, JpaSpecificationExecutor<SportsTitle> {

    default Page<SportsTitle> findAll(SportsTitle filterObj, Pageable pageable) {
        return findAll(CommonSpecifications.nameContains(filterObj.getName()), pageable);
    }

}
