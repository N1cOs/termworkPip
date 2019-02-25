package ru.ifmo.se.termwork.controller.publicApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.dto.WorkerDto;
import ru.ifmo.se.termwork.service.LinkService;
import ru.ifmo.se.termwork.service.SignUpService;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/sign-up")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private LinkService linkService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signUp(@RequestBody StudentDto studentDto) {
        signUpService.signUp(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signUpWorker(@PathVariable String uuid,
                               @RequestBody @Valid WorkerDto workerDto){
        if(!linkService.isValid(uuid))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        signUpService.signUp(workerDto, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
