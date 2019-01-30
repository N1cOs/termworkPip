package ru.ifmo.se.termwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.dto.StudentDTO;
import ru.ifmo.se.termwork.service.SignUpService;

@RestController
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "signup/check", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity checkExistence(@RequestBody StudentDTO studentDTO) {
        int status;
        if (signUpService.checkExistence(studentDTO.getSerialNumber()))
            status = 200;
        else
            status = 201;
        return ResponseEntity.status(status).build();

    }


}
