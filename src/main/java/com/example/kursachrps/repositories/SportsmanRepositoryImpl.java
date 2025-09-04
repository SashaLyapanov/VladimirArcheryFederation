package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Sportsman;
import jakarta.persistence.criteria.Path;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class SportsmanRepositoryImpl implements SportsmanRepositoryCustom {

    private final SportsmanRepository sportsmanRepository;

    public SportsmanRepositoryImpl(@Lazy SportsmanRepository sportsmanRepository) {
        this.sportsmanRepository = sportsmanRepository;
    }

    @Override
    public List<Sportsman> findSportsmenByParams(String surname, String name, String patronymic) {
        return sportsmanRepository.findAll(generateSpec(surname, name, patronymic), Sort.by(Sort.Direction.ASC, "surname"));
    }

    public Specification<Sportsman> generateSpec(String surname, String firstName, String patronymic) {
        return (root, query, criteriaBuilder) -> {
            return where(byLikeString(root.get("surname"), surname))
                    .and(byLikeString(root.get("firstName"), firstName))
                    .and(byLikeString(root.get("patronymic"), patronymic))
                    .toPredicate(root, query.orderBy().distinct(true), criteriaBuilder);
        };
    }

    public static Specification<Sportsman> byLikeString(Path<String> path, String queryString) {
        if (queryString == null || queryString.isEmpty()) return null;
        return (((root, query, criteriaBuilder) -> criteriaBuilder.like(path, "%" + queryString + "%")));
    }

}
