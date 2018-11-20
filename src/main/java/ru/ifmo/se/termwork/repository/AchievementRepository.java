package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

}
