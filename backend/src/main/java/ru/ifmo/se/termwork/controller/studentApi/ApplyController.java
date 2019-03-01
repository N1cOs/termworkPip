package ru.ifmo.se.termwork.controller.studentApi;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.termwork.domain.User;
import ru.ifmo.se.termwork.support.annotation.JsonParam;

@RestController
@RequestMapping("/student")
public class ApplyController {

    @PostMapping(path = "/college/{collegeId}/{okso}")
    public String apply(@AuthenticationPrincipal User student,
                                @PathVariable("collegeId") int collegeId,
                                @PathVariable("okso") String okso,
                                @JsonParam("olympiadId") int olympiadId){
        return "olympiad id is " + olympiadId;

    }
}
