package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.Subject;
import ru.ifmo.se.termwork.repository.SubjectRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.List;

@RestController
@RequestMapping("/public/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable int id){
        return subjectRepository.findById(id).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.subject.invalidId", id));
    }

    @GetMapping
    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }
}
