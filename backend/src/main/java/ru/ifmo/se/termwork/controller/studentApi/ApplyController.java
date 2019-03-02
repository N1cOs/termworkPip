package ru.ifmo.se.termwork.controller.studentApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.ClaimDto;
import ru.ifmo.se.termwork.service.ApplyingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class ApplyController {

    @Autowired
    private ApplyingService applyingService;

    @PostMapping(path = "/speciality", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity apply(@AuthenticationPrincipal User student,
                                @Valid @RequestBody ClaimDto claimDto){
        applyingService.applyForSpeciality(student.getId(), claimDto);
        return ResponseEntity.ok().build();

    }
}
