package ru.ifmo.se.termwork.controller.workerApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.controller.exception.ApiException;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.WorkerDto;
import ru.ifmo.se.termwork.security.Role;
import ru.ifmo.se.termwork.service.LinkService;
import ru.ifmo.se.termwork.service.SignUpService;

@RestController
@RequestMapping("/worker/sign-up")
public class AddWorkerController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private LinkService linkService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addWorker(@AuthenticationPrincipal User user,
                                 @RequestBody WorkerDto workerDto){
        if(!user.hasRole(Role.HEAD_WORKER))
            throw new ApiException(HttpStatus.FORBIDDEN, "exception.signUp.notHeadWorker");

        if(workerDto.getEmail() == null)
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.email");

        if(signUpService.isEmailExists(workerDto.getEmail()))
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.signUp.email");

        linkService.generateWorkerSignUpLink(user.getId(), workerDto.getEmail());
        return ResponseEntity.ok().build();
    }


}
