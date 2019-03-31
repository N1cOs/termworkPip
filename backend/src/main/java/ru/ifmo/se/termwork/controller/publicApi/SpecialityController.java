package ru.ifmo.se.termwork.controller.publicApi;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.termwork.domain.Requirement;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.dto.SpecialitiesDto;
import ru.ifmo.se.termwork.repository.SpecialityRepository;
import ru.ifmo.se.termwork.support.exception.ApiException;

import java.util.List;
import java.util.stream.Collectors;

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
    public SpecialitiesDto getSpecialities(@RequestParam("collegeId") Integer collegeId,
                                           @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                           @RequestParam(name = "offset", defaultValue = "0") Integer offset){
        if(limit < 0 || offset < 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.limit.offset.negative");
        List<Speciality> specialities = specialityRepository.findWithReqAllByCollegeId(collegeId);
        if(specialities.isEmpty())
            throw new ApiException(HttpStatus.BAD_REQUEST, "exception.college.notFound", collegeId);
        return new SpecialitiesDto(specialities.size(),
                specialities.stream().skip(offset).limit(limit).collect(Collectors.toList()));
    }

}
