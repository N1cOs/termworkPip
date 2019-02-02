package ru.ifmo.se.termwork.controller.protectedApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.CredentialDto;
import ru.ifmo.se.termwork.service.ChangeCredentialsService;
import ru.ifmo.se.termwork.service.MessageService;

import javax.validation.Valid;

@RestController
@RequestMapping("/change")
public class ChangeCredentialsController {

    @Autowired
    private ChangeCredentialsService changeCredentialsService;

    @Autowired
    private MessageService messageService;

    @PutMapping(value = "/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changePassword(@AuthenticationPrincipal User user,
                                         @RequestBody @Valid CredentialDto credentialDTO){
        changeCredentialsService.changePassword(user.getId(), credentialDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changeEmail(@AuthenticationPrincipal User user,
                                      @RequestBody @Valid CredentialDto credentialDTO){
        changeCredentialsService.changeEmail(user.getId(), credentialDTO);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleBadRequest(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageService.getAsExceptionDto(e.getMessage()));
    }
}
