package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Olympiad;

import java.util.List;
import java.util.Optional;

public interface OlympiadRepository extends JpaRepository<Olympiad, Integer> {
    /**
     * Returns the olympiad by its name and id of the subject
     *
     * @param name name of the olympiad
     * @param subjectId id of the subject
     * @return the olympiad
     */
    Optional<Olympiad> findByNameIgnoreCaseAndSubjectId(String name, Integer subjectId);

    List<Olympiad> findAllBySubjectId(Integer subjectId);
}
