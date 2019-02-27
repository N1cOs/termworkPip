package ru.ifmo.se.termwork.service.impl;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.service.LinkService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@Log4j
@Service
public class LinkServiceImpl implements LinkService {

    private final static Set<Link> links = new CopyOnWriteArraySet<>();

    private final static int validityInHours = 24;

    @Override
    public String generateLink() {
        String uuid = UUID.randomUUID().toString();
        log.info(uuid);
        Date expirationTime = Date.from(Instant.now().plus(validityInHours, ChronoUnit.HOURS));
        Link link = new Link(uuid, expirationTime);
        links.add(link);
        return uuid;
    }

    @Override
    public boolean isValid(String uuid) {
        return links.stream().filter(l -> l.uuid.equals(uuid)).
                findFirst().map(Link::isValid).orElse(false);
    }

    @Override
    public void destroyLink(String uuid) {
        links.removeIf(l -> l.uuid.equals(uuid));
    }

    //every 20 minutes
    @Scheduled(cron = "0 0/20 * * * ?")
    public void removeInvalidLinks(){
        links.stream().filter(l -> !l.isValid()).forEach(links::remove);
    }

    @EqualsAndHashCode
    private class Link{

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
