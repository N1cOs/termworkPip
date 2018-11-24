package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    /**
     * Returns the student specified by its email and phone
     *
     * @param email email of the student
     * @param phone phone of the student
     * @return the student
     */
    Student findByEmailIgnoreCaseOrPhone(String email, String phone);

    /**
     * Returns the student from database with his or her exams by his or her
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.exams")
    Student findWithExamsById(Integer id);

    /**
     * Returns the student from database with his or her achievements by his or her id
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.achievements")
    Student findWithAchievementsById(Integer id);

    /**
     * Returns the student from database with his or her scores by his or her id
     *
     * @param id id of the student
     * @return
     */
    @EntityGraph("student.scores")
    Student findWithScoresById(Integer id);

    /**
     * Returns the student from database with his or her olympiads by his or her id
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.olympiads")
    Student findWithOlympiadsById(Integer id);


    /**
     * Returns the student from database with his or her ratings by his or her id
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.ratings")
    Student findWithRatingsById(Integer id);


    /**
     * Returns the student from database with his or her messages by his or her id
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.messages")
    Student findWithMessagesById(Integer id);
}
