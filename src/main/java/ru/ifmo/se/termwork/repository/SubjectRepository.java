package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findByNameIgnoreCase(String name);
}
