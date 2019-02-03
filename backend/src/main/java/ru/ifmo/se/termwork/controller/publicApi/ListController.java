package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.Subject;
import ru.ifmo.se.termwork.repository.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping("/public")
public class ListController{

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/exams")
    public List<Subject> getAllExams(){
        return subjectRepository.findAll();
    }
}
