package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

}
