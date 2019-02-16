package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    /**
     * Returns achievement by its name
     *
     * @param name achievement's name
     * @return the achievement which name corresponds to the parameter
     */
    Optional<Achievement> findByNameIgnoreCase(String name);

    default List<Achievement> formAchievement(List<Integer> achievementsDto){
        List<Achievement> achievements = new ArrayList<>();
        achievementsDto.forEach(id->{
            achievements.add(findById(id).get());
        });
        return achievements;
    }
}
