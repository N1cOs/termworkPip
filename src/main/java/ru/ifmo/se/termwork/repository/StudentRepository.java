package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ifmo.se.termwork.domain.Student;

import javax.transaction.Transactional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmailIgnoreCaseOrPhone(String email, String phone);

    @EntityGraph("student.exams")
    Student findWithExamsById(Integer id);

    @EntityGraph("student.achievements")
    Student findWithAchievementsById(Integer id);

    @EntityGraph("student.scores")
    Student findWithScoresById(Integer id);

    @EntityGraph("student.olympiads")
    Student findWithOlympiadsById(Integer id);

    @EntityGraph("student.ratings")
    Student findWithRatingsById(Integer id);

    @EntityGraph("student.messages")
    Student findWithMessagesById(Integer id);
}
