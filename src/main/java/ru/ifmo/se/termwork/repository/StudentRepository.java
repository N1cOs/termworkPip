package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
