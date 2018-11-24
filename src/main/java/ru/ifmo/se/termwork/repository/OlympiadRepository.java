package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Olympiad;

public interface OlympiadRepository extends JpaRepository<Olympiad, Integer> {

    /**
     * Returns the olympiad by its serial number
     *
     * @param serialNumber serial number of the olympiad
     * @return the olympiad
     */
    Olympiad findBySerialNumber(String serialNumber);

    /**
     * Returns the olympiad by its name and id of the subject
     *
     * @param name      name of the olympiad
     * @param subjectId id of the subject
     * @return the olympiad
     */
    Olympiad findByNameIgnoreCaseAndSubjectId(String name, Integer subjectId);
}
