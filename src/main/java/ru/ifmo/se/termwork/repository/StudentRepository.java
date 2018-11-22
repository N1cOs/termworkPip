package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ifmo.se.termwork.domain.Student;

import javax.transaction.Transactional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmailIgnoreCaseOrPhone(String email, String phone);

    @EntityGraph("student.scores")
    Student findStudentWithScoresById(Integer id);

    @EntityGraph("student.olympiads")
    Student findStudentWithOlympiadsById(Integer id);

    @EntityGraph("student.ratings")
    Student findStudentWithRatingsById(Integer id);

    @EntityGraph("student.messages")
    Student findWithMessagesById(Integer id);
}
