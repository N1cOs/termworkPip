package ru.ifmo.se.termwork.controller.publicApi;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.dto.StudentDTO;
//import ru.ifmo.se.termwork.service.Mapper;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.service.SignUpService;

import javax.validation.Valid;

@Log4j
@RestController
@RequestMapping(value = "/public")
public class SignUpController {
    @Autowired
    SignUpService signUpService;



    @PostMapping(path = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signUp(@RequestBody @Valid StudentDTO studentDTO) {
        signUpService.signUp(studentDTO);
        return ResponseEntity.ok("");
    }

    @PostMapping(path = "/check", consumes = "application/json")
    public ResponseEntity checkExistence(@RequestBody StudentDTO studentDTO) {
        int status;
        if (signUpService.checkExistence(studentDTO.getSerialNumber()))
            status = 200;
        else
            status = 201;
        return ResponseEntity.status(status).build();

    }


}
