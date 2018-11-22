package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.College;

import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Integer> {
    @EntityGraph("college.specialities")
    College findWithSpecialitiesById(Integer id);

    @EntityGraph("college.scores")
    College findWithScoresById(Integer id);

    List<College> findAllByNameLikeIgnoreCaseOrAbbreviationLikeIgnoreCase(String name, String abbreviation);

    List<College> findAllByCityIgnoreCase(String city);
}
