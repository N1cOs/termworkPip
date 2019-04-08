package ru.ifmo.se.termwork.controller.protectedApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.domain.Worker;
import ru.ifmo.se.termwork.repository.WorkerRepository;
import ru.ifmo.se.termwork.security.Role;
import ru.ifmo.se.termwork.service.StudentService;
import ru.ifmo.se.termwork.support.exception.ApiException;

@RestController
@RequestMapping("/me")
public class UserController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMe(@AuthenticationPrincipal User user){
        if(user.hasRole(Role.STUDENT)){
            return ResponseEntity.ok(studentService.getStudentDto(user.getId()));
        }
        Worker worker = workerRepository.findById(user.getId()).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.user.notFound"));
        return ResponseEntity.ok(worker);
    }
}
