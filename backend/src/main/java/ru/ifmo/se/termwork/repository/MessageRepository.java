package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.ifmo.se.termwork.domain.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    /**
     * Returns student and worker messages ordered by date they have been sent
     *
     * @param studentId the id of student
     * @param workerId  the id of the worker
     * @return the list of all the messages
     */
    List<Message> findAllByStudentIdAndWorkerIdOrderByDate(Integer studentId, Integer workerId);

    /**
     * Returns student's and worker's messages from a database
     * with links to student and worker specified by their id
     *
     * @param studentId id of the student
     * @param workerId  if of the worker
     * @return the list of messages
     */
    @EntityGraph("messages.all")
    List<Message> findAllWithAttributesByStudentIdAndWorkerIdOrderByDate(Integer studentId, Integer workerId);
}
