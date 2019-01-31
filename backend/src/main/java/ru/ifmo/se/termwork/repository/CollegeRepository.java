package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.College;

import java.util.List;
import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Integer> {
    /**
     * Returns the college from a database with specialities specified by id
     * @see ru.ifmo.se.termwork.domain.Speciality
     *
     * @param id id of the college
     * @return the College
     */
    @EntityGraph("college.specialities")
    Optional<College> findWithSpecialitiesById(Integer id);

    /**
     * Returns the college from a database with college's achievements  specified by id
     * @see ru.ifmo.se.termwork.domain.CollegeAchievement
     *
     * @param id id of the college
     * @return the College
     */
    @EntityGraph("college.scores")
    Optional<College> findWithScoresById(Integer id);

    /**
     * Returns the list of colleges which name or abbreviation corresponds to the parameters
     *
     * @param name name of the college
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
