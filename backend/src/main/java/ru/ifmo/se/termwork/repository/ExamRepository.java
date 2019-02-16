package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Exam;
import ru.ifmo.se.termwork.domain.keys.ExamId;

public interface ExamRepository extends JpaRepository<Exam, ExamId> {

}
