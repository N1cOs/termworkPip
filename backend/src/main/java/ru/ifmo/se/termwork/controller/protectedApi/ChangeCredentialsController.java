package ru.ifmo.se.termwork.controller.protectedApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.CredentialDto;
import ru.ifmo.se.termwork.service.ChangeCredentialsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/change")
public class ChangeCredentialsController {

    @Autowired
    private ChangeCredentialsService changeCredentialsService;

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
}
