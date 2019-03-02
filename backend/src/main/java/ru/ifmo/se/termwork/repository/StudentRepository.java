package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    /**
     * Saves a given student. Database can throw exceptions with error codes:
     * <p>
     * 23030 - email constraint violation
     * 23031 - serial number constraint violation
     * 23032 - phone constraint violation
     * 23033 - email and phone constraint violation
     * 23034 - email and serial number constraint violation
     * 23035 - phone and serial number constraint violation
     * 23036 - email, phone and serial number constraint violation
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     * @see SqlError
     */
    @Override
    <S extends Student> S save(S entity);

    /**
     * Returns the student specified by its email
     *
     * @param email email of the student
     * @return the student
     */
    Optional<Student> findByEmail(String email);

    Optional<Student> findBySerialNumber(String serialNumber);

    /**
     * Returns the student from a database with exams by specified id
     *
     * @param id id of the student
     * @return the student
     * @see ru.ifmo.se.termwork.domain.Exam
     */
    @EntityGraph("student.exams")
    Optional<Student> findWithExamsById(Integer id);

    /**
     * Returns the student from a database with achievements by specified id
     *
     * @param id id of the student
     * @return the student
     * @see ru.ifmo.se.termwork.domain.Achievement
     */
    @EntityGraph("student.achievements")
    Optional<Student> findWithAchievementsById(Integer id);

    /**
     * Returns the student from a database with exams and achievements by specified id
     *
     * @param id id of the student
     * @return the student
     * @see ru.ifmo.se.termwork.domain.Exam
     * @see ru.ifmo.se.termwork.domain.Achievement
     */
    @EntityGraph("student.scores")
    Optional<Student> findWithScoresById(Integer id);

    /**
     * Returns the student from a database with olympiad by specified id
     *
     * @param id id of the student
     * @return the student
     * @see ru.ifmo.se.termwork.domain.Olympiad
     */
    @EntityGraph("student.olympiads")
    Optional<Student> findWithOlympiadsById(Integer id);


    /**
     * Returns the student from a database with ratings by specified id
     *
     * @param id id of the student
     * @return the student
     * @see ru.ifmo.se.termwork.domain.Rating
     */
    @EntityGraph("student.ratings")
    Optional<Student> findWithRatingsById(Integer id);

    @EntityGraph("student.forApplying")
    Optional<Student> findForApplyingById(Integer id);

    interface SqlError {

        int EMAIL = 23030;

        int SERIAL_NUMBER = 23031;

        int PHONE = 23032;

        int EMAIl_PHONE = 23033;

        int EMAIL_SERIAL_NUMBER = 23034;

        int SERIAL_NUMBER_PHONE = 23035;

        int ALL = 23036;
    }

}
