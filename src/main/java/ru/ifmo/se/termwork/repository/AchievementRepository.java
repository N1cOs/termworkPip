package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Achievement;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    /**
     * The method that performs a search for achievements by achievement name
     *
     * @param name the name
     * @return the achievement named as the name we are looking for
     */
    Achievement findByNameIgnoreCase(String name);
}
