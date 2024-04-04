package com.example.kursachrps.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CommonSpecifications {
    public static <T> Specification<T> nameContains(String part) {
        return ((root, query, criteriaBuilder) -> part != null ? criteriaBuilder.and(nameContains(root, criteriaBuilder, part)): null);
    }

    public static <T> Specification<T> placeContains(String part) {
        return ((root, query, criteriaBuilder) -> part != null ? criteriaBuilder.and(placeContains(root, criteriaBuilder, part)): null);
    }

    public static <T> Predicate nameContains(Root<T> root, CriteriaBuilder criteriaBuilder, String part) {
        Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + part + "%");
        return criteriaBuilder.and(namePredicate);
    }

    public static <T> Predicate placeContains(Root<T> root, CriteriaBuilder criteriaBuilder, String part) {
        Predicate placePredicate = criteriaBuilder.like(root.get("place"), "%" + part + "%");
        return criteriaBuilder.and(placePredicate);
    }
}
