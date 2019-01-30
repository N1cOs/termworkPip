package ru.ifmo.se.termwork.controller.protectedApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.dto.CredentialDTO;
import ru.ifmo.se.termwork.security.Authorities;
import ru.ifmo.se.termwork.service.ChangeCredentialsService;

import java.util.Optional;

@RestController
@RequestMapping("/change")
public class ChangeCredentialsController {

    @Autowired
    private ChangeCredentialsService changeCredentialsService;

    @PutMapping(value = "/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changePassword(@AuthenticationPrincipal User user,
                                         @RequestBody CredentialDTO credentialDTO){
        int userId = Integer.parseInt(user.getUsername());
        Optional<String> authority = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).
                filter(s -> s.equals(Authorities.STUDENT) || s.equals(Authorities.WORKER)).findFirst();
        changeCredentialsService.changePassword(userId, credentialDTO, authority.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changeEmail(@AuthenticationPrincipal User user,
                                      @RequestBody CredentialDTO credentialDTO){
        Optional<String> authority = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).
                filter(s -> s.equals(Authorities.STUDENT) || s.equals(Authorities.WORKER)).findFirst();
        int userId = Integer.parseInt(user.getUsername());
        changeCredentialsService.changeEmail(userId, credentialDTO, authority.get());
        return ResponseEntity.ok().build();
    }

}
