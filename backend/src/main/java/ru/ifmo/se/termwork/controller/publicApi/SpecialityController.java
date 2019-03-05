package ru.ifmo.se.termwork.controller.publicApi;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.Requirement;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.repository.SpecialityRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.List;

@RestController
@RequestMapping("/public")
public class SpecialityController {

    @Autowired
    private SpecialityRepository specialityRepository;

    @JsonView(Requirement.class)
    @GetMapping("/specialities/{id}")
    public Speciality getSpeciality(@PathVariable("id") Integer id){
        return specialityRepository.findWithReqsById(id).
                orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "exception.speciality.notFound", id));
    }

    @JsonView(Requirement.class)
    @GetMapping("/specialities")
    public List<Speciality> getSpecialities(@RequestParam("college") Integer collegeId){
        return specialityRepository.findWithReqAllByCollegeId(collegeId);
    }

}
