package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Olympiad;

public interface OlympiadRepository extends JpaRepository<Olympiad, Integer> {

    Olympiad findBySerialNumber(String serialNumber);

    Olympiad findByNameIgnoreCaseAndSubjectId(String name, Integer subjectId);
}
