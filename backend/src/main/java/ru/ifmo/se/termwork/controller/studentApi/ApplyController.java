package ru.ifmo.se.termwork.controller.studentApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.dto.ClaimDto;
import ru.ifmo.se.termwork.service.ApplyingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/student/speciality/{id}")
public class ApplyController {

    @Autowired
    private ApplyingService applyingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity apply(@AuthenticationPrincipal User student,
                                @PathVariable("id") int specialityId,
                                @Valid @RequestBody ClaimDto claimDto){
        claimDto.setSpecialityId(specialityId);
        applyingService.applyForSpeciality(student.getId(), claimDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity cancel(@AuthenticationPrincipal User user,
                                 @PathVariable("id") int specialityId){
        applyingService.cancelApplication(user.getId(), specialityId);
        return ResponseEntity.ok().build();
    }
}
