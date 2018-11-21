package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.College;

public interface CollegeRepository extends JpaRepository<College, Integer> {
    @EntityGraph("college.specialities")
    College findWithSpecialitiesById(Integer id);
}
