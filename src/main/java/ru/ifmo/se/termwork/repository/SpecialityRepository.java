package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    @EntityGraph(value = "speciality.ratings")
    Speciality findWithRatingsById(Integer id);

    @EntityGraph(value = "speciality.req")
    Speciality findWithReqsById(Integer id);

    @EntityGraph(value = "speciality.all")
    Speciality findWithAllById(Integer id);
}
