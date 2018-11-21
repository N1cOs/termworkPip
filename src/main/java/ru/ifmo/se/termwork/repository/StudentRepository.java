package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
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

    @Modifying
    @Query(value = "insert into student_olympiad values (?1, ?2)", nativeQuery = true)
    void addOlympiad(Integer studentId, Integer olympiadId);

    @Modifying
    @Query(value = "delete from student_olympiad where id_student = ?1 and id_olympiad = ?2",
            nativeQuery = true)
    void deleteOlympiad(Integer studentId, Integer olympiadId);

    @EntityGraph("student.scores")
    Student findStudentWithScoresById(Integer id);

    @EntityGraph("student.olympiads")
    Student findStudentWithOlympiadsById(Integer id);

    @EntityGraph("student.ratings")
    Student findStudentWithRatingsById(Integer id);

    @EntityGraph("student.messages")
    Student findWithMessagesById(Integer id);
}
