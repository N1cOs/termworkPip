package ru.ifmo.se.termwork.controller.workerApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.repository.UserRepository;
import ru.ifmo.se.termwork.security.Role;
import ru.ifmo.se.termwork.service.SignUpService;
import ru.ifmo.se.termwork.support.annotation.JsonParam;
import ru.ifmo.se.termwork.support.exception.ApiException;
import ru.ifmo.se.termwork.support.exception.ClientException;
import ru.ifmo.se.termwork.support.exception.InputErrors;

@RestController
@RequestMapping("/worker/sign-up")
public class AddWorkerController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addWorker(@AuthenticationPrincipal User user,
                                    @JsonParam("email") String email){
        if(!user.hasRole(Role.HEAD_WORKER))
            throw new ClientException(HttpStatus.FORBIDDEN, "exception.signUp.notHeadWorker");

        if(email == null)
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.email");

        if(userRepository.findByEmail(email).isPresent())
            throw new ClientException(HttpStatus.BAD_REQUEST, InputErrors.Duplicate.EMAIL);
        signUpService.addWorkerSignUp(user.getId(), email);
        return ResponseEntity.ok().build();
    }


}
