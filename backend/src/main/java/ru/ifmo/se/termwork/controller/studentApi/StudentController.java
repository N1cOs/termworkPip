package ru.ifmo.se.termwork.controller.studentApi;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;

@RestController
@RequestMapping("/student/{id}")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @JsonView(User.View.Scores.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "You can't get information about another student")
    })
    public Student getStudent(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Integer studentId){
        if(user.getId() == studentId){
            return studentRepository.findWithScoresById(studentId).
                    orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        }
        throw new ApiException(HttpStatus.FORBIDDEN, "exception.forbidden");
    }
}
