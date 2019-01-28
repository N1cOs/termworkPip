package ru.ifmo.se.termwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.dto.StudentDTO;
import ru.ifmo.se.termwork.service.impl.SignUpServiceImpl;

@RestController

public class LoginController {

    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = "application/json")
    public String test(@RequestBody StudentDTO studentDTO) {
        return studentDTO.getSerialNumber();
    }

    @Autowired
    private SignUpServiceImpl signUpService;

    @RequestMapping(value = "/test/check", method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity testCheck(@RequestBody StudentDTO studentDTO) {
        if (signUpService.checkExistence(studentDTO.getSerialNumber())) {
            return ResponseEntity.status(200).build();
        } else
            return ResponseEntity.status(201).build();
    }
}
