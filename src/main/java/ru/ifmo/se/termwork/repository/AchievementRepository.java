package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Achievement;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    /**
     * Returns achievement by its name
     *
     * @param name achievement's name
     * @return the achievement which name corresponds to the parameter
     */
    Achievement findByNameIgnoreCase(String name);
}
