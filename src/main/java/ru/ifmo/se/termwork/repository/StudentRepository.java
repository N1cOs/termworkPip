package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ifmo.se.termwork.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Modifying
    @Query(value = "insert into exam values (?1, ?2, ?3)", nativeQuery = true)
    void addExam(Integer studentId, Integer subjectId, Integer score);

    @Modifying
    @Query(value = "update exam set score = ?3 where id_student = ?1 and id_subj = ?2",
            nativeQuery = true)
    void updateExam(Integer studentId, Integer subjectId, Integer score);

    @Modifying
    @Query(value = "delete from exam where id_student = ?1 and id_sudj = ?2", nativeQuery = true)
    void deleteExam(Integer studentId, Integer subjectId);

}
