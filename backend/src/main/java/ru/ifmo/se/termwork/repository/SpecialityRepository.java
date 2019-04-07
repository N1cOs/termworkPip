package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ifmo.se.termwork.domain.Speciality;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    /**
     * Returns speciality found by its name and college name
     *
     * @param name name of the speciality
     * @param collegeName name of the college
     * @return the speciality
     */
    Optional<Speciality> findByNameIgnoreCaseAndCollegeNameIgnoreCase(String name, String collegeName);

    /**
     * Returns the list of specialities which name corresponds to the specified pattern
     *
     * @param patternName pattern for speciality's name
     * @return list of the specialities
     */
    List<Speciality> findByNameLikeIgnoreCase(String patternName);

    /**
     * Returns the speciality by its id from a database with ratings
     * @see ru.ifmo.se.termwork.domain.Rating
     *
     * @param id speciality id
     * @return the speciality
     */
    @EntityGraph(value = "speciality.ratings")
    Optional<Speciality> findWithRatingsById(Integer id);

    /**
     * Returns the speciality from a database with its requirements by speciality id
     * @see ru.ifmo.se.termwork.domain.Requirement
     *
     * @param id speciality id
     * @return the speciality
     */
    @EntityGraph(value = "speciality.req")
    Optional<Speciality> findWithReqsById(Integer id);

    /**
     * Returns the speciality from a database with all the attributes by speciality id
     *
     * @param id speciality id
     * @return the speciality
     */
    @EntityGraph(value = "speciality.all")
    Optional<Speciality> findWithAllById(Integer id);

    @EntityGraph(value = "speciality.req")
    List<Speciality> findWithReqAllByCollegeIdOrderById(Integer id);
}
