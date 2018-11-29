package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    /**
     * Returns the subject by its name
     *
     * @param name subject name
     * @return the subject
     */
    Subject findByNameIgnoreCase(String name);
}
