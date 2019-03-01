package ru.ifmo.se.termwork.controller.studentApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.support.exception.ApiException;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.ExamDto;
import ru.ifmo.se.termwork.service.ExamService;

import javax.validation.Validator;
import java.util.List;

@RestController
@RequestMapping("/student/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private Validator validator;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addExams(@AuthenticationPrincipal User user,
                                  @RequestBody List<ExamDto> exams){
        if(isValid(exams)) {
            examService.saveExams(user.getId(), exams);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "exception.exam.invalidFormat");
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateExams(@AuthenticationPrincipal User user,
                                   @RequestBody List<ExamDto> exams){
        if(isValid(exams)) {
            examService.saveExams(user.getId(), exams);
            return ResponseEntity.ok().build();
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "exception.exam.invalidFormat");
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteExams(@AuthenticationPrincipal User user,
                                      @RequestBody List<ExamDto> exams){
        if(isValid(exams)) {
            examService.deleteExams(user.getId(), exams);
            return ResponseEntity.ok().build();
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "exception.exam.invalidFormat");
    }

    private boolean isValid(List<ExamDto> exams){
        return exams.stream().noneMatch(l -> validator.validate(l).size() > 0);
    }
}
