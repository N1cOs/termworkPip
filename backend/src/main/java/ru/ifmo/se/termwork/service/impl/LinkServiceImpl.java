package ru.ifmo.se.termwork.service.impl;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.controller.exception.ApiException;
import ru.ifmo.se.termwork.domain.Worker;
import ru.ifmo.se.termwork.dto.WorkerDto;
import ru.ifmo.se.termwork.repository.WorkerRepository;
import ru.ifmo.se.termwork.service.LinkService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
@Service
public class LinkServiceImpl implements LinkService {

    private final static Map<Link, WorkerDto> links = new ConcurrentHashMap<>();

    private final static int validityInHours = 24;

    @Autowired
    private WorkerRepository workerRepository;

    //ToDo: add sending email
    @Async
    @Override
    public void generateWorkerSignUpLink(int headWorkerId, String workerEmail) {
        Worker worker = workerRepository.findById(headWorkerId).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.userNotFound"));
        WorkerDto newWorker = new WorkerDto();
        newWorker.setEmail(workerEmail);
        newWorker.setCollegeId(worker.getCollege().getId());

        String uuid = UUID.randomUUID().toString();
        log.info(uuid);
        Date expirationTime = Date.from(Instant.now().plus(validityInHours, ChronoUnit.HOURS));
        Link link = new Link(uuid, expirationTime);
        links.put(link, newWorker);
    }

    @Override
    public boolean isValid(String uuid) {
        return links.keySet().stream().filter(l -> l.uuid.equals(uuid)).
                findFirst().map(Link::isValid).orElse(false);
    }

    //every 20 minutes
    @Scheduled(cron = "0 0/20 * * * ?")
    public void removeInvalidLinks(){
        links.keySet().stream().filter(l -> !l.isValid()).forEach(links::remove);
    }

    @EqualsAndHashCode
    private static class Link{

        String uuid;

        Date expirationDate;

        Link(String uuid, Date expirationDate) {
            this.uuid = uuid;
            this.expirationDate = expirationDate;
        }

        boolean isValid(){
            return Instant.now().compareTo(expirationDate.toInstant()) < 0;
        }
    }
}
