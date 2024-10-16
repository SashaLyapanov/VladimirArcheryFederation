package com.example.kursachrps.repositories;

import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.CompetitionType;
import com.example.kursachrps.models.DateInterval;
import jakarta.persistence.criteria.Path;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.kursachrps.utils.DateUtils.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CompetitionRepositoryImpl implements CompetitionRepositoryCustom {

    private final CompetitionRepository competitionRepository;

    public CompetitionRepositoryImpl(@Lazy CompetitionRepository competitionRepository) { this.competitionRepository = competitionRepository; }

    @Override
    public Page<Competition> findCompetitionByParams(String name, Date date, CompetitionType type, Pageable pageable) {
        return competitionRepository.findAll(generateSpec(name, date, type), pageable);
    }

    public Specification<Competition> generateSpec(String name, Date date, CompetitionType type) {
        return (root, query, criteriaBuilder) -> {
            return where(byLikeString(root.get("name"), name))
//                    .and(byDate(root.get("date"), date))
//                    .and(byCompetitionType(root.get("type").get("id"), type))
                    .toPredicate(root, query.orderBy().distinct(true), criteriaBuilder);
        };
    }

    public static Specification<Competition> byCompetitionType(Path<String> path, CompetitionType competitionType) {
        if (competitionType == null || competitionType.getId() == null) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(path, competitionType.getId()));
    }

    public static Specification<Competition> byLikeString(Path<String> path, String queryString) {
        if (queryString == null || queryString.isEmpty()) return null;
        return (((root, query, criteriaBuilder) -> criteriaBuilder.like(path, "%" + queryString + "%")));
    }

    public static Specification<Competition> byDate(Path<String> path, Date date) {
        if (date == null) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(path, date));
    }

    public static Specification<Competition> betweenDateInterval(Path<Date> path, DateInterval dateInterval) {
        if (dateInterval == null) return null;
        Date endDate = dateInterval.getEndDateTime() != null ? getDateWithoutTime(getTomorrowDate(dateInterval.getEndDateTime())) : MAX_DATE;
        Date startDate = dateInterval.getStartDateTime() != null ? getDateWithoutTime(dateInterval.getStartDateTime()) : MIN_DATE;
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(path, startDate), criteriaBuilder.lessThanOrEqualTo(path, endDate));
    }

}
