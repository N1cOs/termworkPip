package ru.ifmo.se.termwork.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.termwork.domain.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    /**
     * Returns the worker from a database with messages by worker's id
     * @see ru.ifmo.se.termwork.domain.Message
     *
     * @param id worker's id
     * @return the worker
     */
    @EntityGraph("worker.messages")
    Worker findWithMessagesById(Integer id);
}
