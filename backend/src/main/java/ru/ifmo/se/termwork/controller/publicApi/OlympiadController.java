package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.Olympiad;
import ru.ifmo.se.termwork.repository.OlympiadRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.List;

@RestController
@RequestMapping("/public/olympiads")
public class OlympiadController {

    @Autowired
    private OlympiadRepository olympiadRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Olympiad> getOlympiads(@RequestParam(value = "subjectId", required = false)
                                                   Integer subjectId){
        if(subjectId == null){
            return olympiadRepository.findAll();
        }
        if(subjectId > 0){
            return olympiadRepository.findAllBySubjectId(subjectId);
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "exception.olympiad.subject.negative");
    }
}
