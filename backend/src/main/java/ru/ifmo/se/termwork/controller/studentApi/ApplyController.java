package ru.ifmo.se.termwork.controller.studentApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.service.ApplyingService;
import ru.ifmo.se.termwork.support.annotation.JsonParam;

@RestController
@RequestMapping("/student")
public class ApplyController {

    @Autowired
    private ApplyingService applyingService;

    @PostMapping(path = "/speciality/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity apply(@AuthenticationPrincipal User student,
                                @PathVariable("id") int specialityId,
                                @JsonParam("olympiadId") int olympiadId){
        applyingService.applyForSpeciality(student.getId(), specialityId, olympiadId);
        return ResponseEntity.ok().build();

    }
}
