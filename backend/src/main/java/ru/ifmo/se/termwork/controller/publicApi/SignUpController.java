package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.service.SignUpService;

@RestController
@RequestMapping("/public/sign-up")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signUp(@RequestBody StudentDto studentDto) {
        signUpService.signUp(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
