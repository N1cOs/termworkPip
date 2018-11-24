package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.College;

import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Integer> {
    /**
     * Returns the college from database with the speciality specified by id
     *
     * @param id id of the college
     * @return the College
     */
    @EntityGraph("college.specialities")
    College findWithSpecialitiesById(Integer id);

    /**
     * Returns the college from database with the scores specified by id
     *
     * @param id id of the college
     * @return the College
     */
    @EntityGraph("college.scores")
    College findWithScoresById(Integer id);

    /**
     * Returns the list of colleges by their name and abbreviation
     *
     * @param name         name of the college
     * @param abbreviation abbreviation of the college
     * @return the list of colleges
     */
    List<College> findAllByNameLikeIgnoreCaseOrAbbreviationLikeIgnoreCase(String name, String abbreviation);

    /**
     * Returns the list of colleges specified by city
     *
     * @param city name of the city
     * @return the list of colleges
     */
    List<College> findAllByCityIgnoreCase(String city);
}
