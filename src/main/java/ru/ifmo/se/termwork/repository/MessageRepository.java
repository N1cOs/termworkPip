package ru.ifmo.se.termwork.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.se.termwork.domain.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAllByStudentIdAndWorkerIdOrderByDate(Integer studentId, Integer workerId);
}
