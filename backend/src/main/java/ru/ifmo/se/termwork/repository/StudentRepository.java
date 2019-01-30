package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    /**
     * Returns the student specified by its email and phone
     *
     * @param email email of the student
     * @return the student
     */
    Student findByEmail(String email);

    Student findByEmailAndPassword(String email, String password);

    /**
     * Returns the student from a database with exams by specified id
     * @see ru.ifmo.se.termwork.domain.Exam
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.exams")
    Student findWithExamsById(Integer id);

    /**
     * Returns the student from a database with achievements by specified id
     * @see ru.ifmo.se.termwork.domain.Achievement
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.achievements")
    Student findWithAchievementsById(Integer id);

    /**
     * Returns the student from a database with exams and achievements by specified id
     * @see ru.ifmo.se.termwork.domain.Exam
     * @see ru.ifmo.se.termwork.domain.Achievement
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.scores")
    Student findWithScoresById(Integer id);

    /**
     * Returns the student from a database with olympiad by specified id
     * @see ru.ifmo.se.termwork.domain.Olympiad
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.olympiads")
    Student findWithOlympiadsById(Integer id);


    /**
     * Returns the student from a database with ratings by specified id
     * @see ru.ifmo.se.termwork.domain.Rating
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.ratings")
    Student findWithRatingsById(Integer id);


    /**
     * Returns the student from a database with messages by specified id
     * @see ru.ifmo.se.termwork.domain.Message
     *
     * @param id id of the student
     * @return the student
     */
    @EntityGraph("student.messages")
    Student findWithMessagesById(Integer id);

    Student findBySerialNumber(String serialNumber);

}
